package node;

import common.Identifier;
import common.Parameter;
import common.Type;
import common.Util;
import common.VariableDeclaration;
import ir3.CData3;
import ir3.CMethod3;
import ir3.Program3;
import ir3.statement.Statement3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import node.expression.CallExpression;
import node.expression.IntLiteral;
import node.statement.ReturnStatement;

public class Program implements Node {

  private final MainClass mainClass;
  private final ArrayList<ClassDeclaration> classDeclarations;
  private final Map<Type, ClassDescriptor> classDescriptorMap = new HashMap<>();

  // global states that store counters and tracks error messages

  private static int COUNTER = 0;
  private static int LABEL_COUNTER = 0;

  public static Identifier getNewTempId() {
    return new Identifier("_t" + COUNTER++);
  }

  public static int getNewLabel() {
    return LABEL_COUNTER++;
  }

  // end global states

  public Program(MainClass mainClass, ArrayList<ClassDeclaration> classDeclarations) {
    COUNTER  = 0;
    LABEL_COUNTER = 0;
    this.mainClass = mainClass;
    this.classDeclarations = classDeclarations;
  }

  @Override
  public String toString() {
    return mainClass.toString() + "\n" + Util.joinWithNewLine(classDeclarations);
  }
  
  public boolean isValid() {
    if (!buildClassDescriptorMap()) {
      return false;
    }
    boolean isValid = true;
    Environment mainEnv = new Environment(
            new ClassDescriptor(mainClass.type), mainClass.parameters, mainClass.body.variableDeclarations, Type.VOID);
    Type actualType = mainClass.body.getType(mainEnv, classDescriptorMap);
    if (actualType.isErrorType()) {
      isValid = false;
    } else if (!actualType.equals(Type.VOID)) {
      Util.printLocationAndError(mainClass.type.loc, "Expected return type of main to be Void, instead got " + actualType);
      isValid = false;
    }
    for (ClassDeclaration cd: classDeclarations) {
      if (!cd.isValid(classDescriptorMap)) {
        isValid = false; // we don't just return here, because we want to check all the other classes.
      }
    }
    return isValid;
  }

  private boolean buildClassDescriptorMap() {
    boolean hasErrors = false; // we want to log as many errors as possible, so we only exit after checking each class
    // add primitives
    classDescriptorMap.put(Type.INT, new ClassDescriptor(Type.INT));
    classDescriptorMap.put(Type.BOOL, new ClassDescriptor(Type.BOOL));
    classDescriptorMap.put(Type.STRING, new ClassDescriptor(Type.STRING));
    classDescriptorMap.put(Type.VOID, new ClassDescriptor(Type.VOID));
    // add Main
    classDescriptorMap.put(mainClass.type, new ClassDescriptor(mainClass.type));
    // the `main` method can never be called since it's a reserved word, so we just add an empty
    // class descriptor and check the parameters and body.
    Set<Identifier> seenIds = new HashSet<>();
    for (Parameter param: mainClass.parameters) {
      // 1(c) No two parameters in a method declaration can have the same name.
      if (seenIds.contains(param.identifier)) {
        Util.printLocationAndError(param.identifier.loc, "Duplicate parameter " + param.identifier + " in main class ");
        hasErrors = true;
      }
      seenIds.add(param.identifier);
    }
    // check method body variables
    seenIds = new HashSet<>();
    for (VariableDeclaration vd: mainClass.body.variableDeclarations) {
      if (seenIds.contains(vd.identifier)) {
        Util.printLocationAndError(vd.identifier.loc, "Variable " + vd.identifier + " already declared in method body of main class.");
        hasErrors = true;
      }
      seenIds.add(vd.identifier);
    }
    // the main method can never be called, as `main` is a reserved word.


    // add user declared classes
    for (ClassDeclaration classDeclaration: classDeclarations) {
      // 1(b) No two classes can be declared in a program with the same (class) name.
      if (classDescriptorMap.containsKey(classDeclaration.type)) {
        Util.printLocationAndError(classDeclaration.type.loc, "Duplicate class " + classDeclaration.type);
        hasErrors = true;
      }
      ClassDescriptor classDescriptor = new ClassDescriptor(classDeclaration.type);
      for (VariableDeclaration decl: classDeclaration.variableDeclarations) {
        // 1(a) No two fields in a class can have the same name.
        if (classDescriptor.fields.containsKey(decl.identifier)) {
          Util.printLocationAndError(decl.identifier.loc, "Duplicate field " + decl.identifier + " in class " + classDeclaration.type);
          hasErrors = true;
        }
        classDescriptor.fields.put(decl.identifier, decl.type);
      }
      int methodCounter = 0;
      for (MethodDeclaration decl: classDeclaration.methodDeclarations) {
        seenIds = new HashSet<>();
        List<Type> parameterTypes = new ArrayList<>();
        // check parameters
        for (Parameter param: decl.parameters) {
          // 1(c) No two parameters in a method declaration can have the same name.
          if (seenIds.contains(param.identifier)) {
            Util.printLocationAndError(param.identifier.loc, "Duplicate parameter " + param.identifier + " in method " + decl.identifier + " in class " + classDeclaration.type);
            hasErrors = true;
          }
          seenIds.add(param.identifier);
          parameterTypes.add(param.type);
        }
        // check method body variables
        seenIds = new HashSet<>();
        for (VariableDeclaration vd: decl.methodBody.variableDeclarations) {
          if (seenIds.contains(vd.identifier)) {
            Util.printLocationAndError(vd.identifier.loc, "Variable " + vd.identifier + " already declared in method body.");
            hasErrors = true;
          }
          seenIds.add(vd.identifier);
        }
        MethodSignature sig = new MethodSignature(decl.identifier, parameterTypes);
        // 1(d) No two methods within a class declaration can have the same name AND parameter types.
        // (overloading is accepted)
        if (classDescriptor.methods.containsKey(sig)) {
          Util.printLocationAndError(decl.identifier.loc, "Duplicate method " + sig + " in class " + classDeclaration.type);
          hasErrors = true;
        }
        decl.methodNumber = methodCounter++; // give the method a number
        classDescriptor.methods.put(sig, decl); // map the signature methodName(type1, type2 ...) to the actual declaration
      }
      classDescriptorMap.put(classDeclaration.type, classDescriptor);
    }
    return !hasErrors;
  }

  public Program3 toIR3() {
    Program3.STRING_ADDRESS_MAP.clear();
    Map<Type, CData3> classMap = new HashMap<>();
    ArrayList<CMethod3> methods = new ArrayList<>();
    ArrayList<CData3> datas = new ArrayList<>();
    // add main method
    List<VariableDeclaration> tempDeclarations = new ArrayList<>();
    mainClass.body.statements.statements.add(new ReturnStatement(new IntLiteral(0), null));
    List<Statement3> converted = mainClass.body.statements.toIR3(tempDeclarations);
    tempDeclarations.addAll(mainClass.body.variableDeclarations);
    List<Parameter> parameters = new ArrayList<>();
    parameters.add(new Parameter(mainClass.type, Identifier.THIS));
    parameters.addAll(mainClass.parameters);
    CMethod3 mainMethod = new CMethod3(Type.VOID, new Identifier("main"), parameters, tempDeclarations, converted, classMap);
    // add class methods;
    for (ClassDeclaration cd: classDeclarations) {
      CData3 data = new CData3(cd.type, cd.variableDeclarations);
      classMap.put(cd.type, data);
      for (MethodDeclaration md: cd.methodDeclarations) {
        tempDeclarations = new ArrayList<>();
        converted = md.methodBody.statements.toIR3(tempDeclarations);
        tempDeclarations.addAll(md.methodBody.variableDeclarations);
        parameters = new ArrayList<>();
        parameters.add(new Parameter(cd.type, Identifier.THIS));
        parameters.addAll(md.parameters);
        methods.add(new CMethod3(md.type,
                new Identifier(CallExpression.getHashedMethodName(cd.type, md.methodNumber)),
                parameters,
                tempDeclarations,
                converted,
                classMap
                ));
      }
    }
    methods.add(mainMethod);
    return new Program3(datas, methods);
  }
}

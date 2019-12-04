package node.expression;

import common.Location;
import common.VariableDeclaration;
import ir3.expression.CallExpression3;
import ir3.expression.Expression3;
import ir3.expression.IdentifierExpression3;
import ir3.expression.ThisExpression3;
import ir3.statement.AssignmentStatement3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Identifier;
import node.MethodDeclaration;
import node.MethodSignature;
import node.Program;
import common.Type;
import common.Util;

public class CallExpression extends Expression {

  private final Expression callee;
  private final List<Expression> arguments;
  private final Location loc;

  private Type objectType;
  private int methodNumber;

  private Type type;

  public CallExpression(Expression callee, ArrayList<Expression> arguments, Location loc) {
    this.callee = callee;
    this.arguments = arguments;
    this.loc = loc;
  }

  @Override
  public String toString() {
    return "[" + callee.toString() + "(" + Util.listToString(arguments) + ")]";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    // if it's a call like method(arg1, arg2), we treat it like this.method(arg1, arg2)
    Identifier methodId;
    if (callee instanceof IdentifierExpression) {
      objectType = env.thisType;
      methodId = ((IdentifierExpression) callee).identifier;
    } else if (callee instanceof MemberExpression) {
      MemberExpression me = ((MemberExpression) callee);
      objectType = me.object.getType(env, classDescriptorMap);
      if (objectType.isErrorType()) {
        return Type.ERROR;
      }
      methodId = me.property;
    } else {
      Util.printLocationAndError(loc, "Illegal method call : " + this);
      return Type.ERROR;
    }

    if (!classDescriptorMap.containsKey(objectType)) {
      Util.printLocationAndError(loc, objectType.loc + ": Class " + objectType + " does not exist");
      return Type.ERROR;
    }

    ClassDescriptor cd = classDescriptorMap.get(objectType);
    List<Type> actualParamTypes = new ArrayList<>();
    for (Expression arg: arguments) {
      Type argType = arg.getType(env, classDescriptorMap);
      if (argType.isErrorType()) {
        return Type.ERROR;
      }
      actualParamTypes.add(argType);
    }

    MethodSignature ms = new MethodSignature(methodId, actualParamTypes);

    if (!cd.methods.containsKey(ms)) {
      Util.printLocationAndError(methodId.loc, "Method " + ms + " does not exist in class " + objectType);
      return Type.ERROR;
    }

    MethodDeclaration returnPair = cd.methods.get(ms);

    methodNumber = returnPair.methodNumber; // keep track of method number for later IR3 gen
    this.type = returnPair.type;
    return returnPair.type;
  }

  @Override
  public IR3Pair toIR3(List<VariableDeclaration> tempDeclarations) {
    Expression3 thisExpr;
    ArrayList<AssignmentStatement3> result = new ArrayList<>();
    if (callee instanceof IdentifierExpression) { // it's a `this`.method() call
      thisExpr = new ThisExpression3();
    } else { // must be a member expression now
      MemberExpression calleeME = (MemberExpression) callee;
      if (calleeME.object instanceof ThisExpression) {
        thisExpr = new ThisExpression3(); // same as previous case
      } else { // else we resolve the object to IR3
        IR3Pair calleePair = calleeME.object.toIR3(tempDeclarations);
        thisExpr = calleePair.idc3;
        result.addAll(calleePair.statements);
      }
    }
    ArrayList<Expression3> args = new ArrayList<>();
    args.add(thisExpr);
    for (Expression arg: arguments) { // IR3-ify the arguments first
      IR3Pair argPair = arg.toIR3(tempDeclarations);
      args.add(argPair.idc3);
      result.addAll(argPair.statements);
    }

    Identifier temp = Program.getNewTempId();
    tempDeclarations.add(new VariableDeclaration(this.type, temp));
    result.add(new AssignmentStatement3(temp, new CallExpression3(
            new IdentifierExpression3(new Identifier(getHashedMethodName(objectType, methodNumber)))
            , args)));
    return new IR3Pair(new IdentifierExpression3(temp), result);
  }

  public static String getHashedMethodName(Type className, int methodNumber) {
    return "_" + className + "_" + methodNumber;
  }
}

package node;

import common.Type;
import common.Util;
import common.VariableDeclaration;
import java.util.ArrayList;
import java.util.Map;

public class ClassDeclaration implements Node {

  public Type type;
  public final ArrayList<VariableDeclaration> variableDeclarations;
  public final ArrayList<MethodDeclaration> methodDeclarations;

  public ClassDeclaration(Type type, ArrayList<VariableDeclaration> variableDeclarations, ArrayList<MethodDeclaration> methodDeclarations) {
    this.type = type;
    this.variableDeclarations = variableDeclarations;
    this.methodDeclarations = methodDeclarations;
  }

  @Override
  public String toString() {
    return String.format("class %s {\n%s\n%s}", type.toString(), Util.indent(Util.joinWithNewLine(variableDeclarations)),
        Util.indent(Util.joinWithNewLine(methodDeclarations)));
  }

  public boolean isValid(Map<Type, ClassDescriptor> classDescriptorMap) {
    ClassDescriptor cd = classDescriptorMap.get(type);
    boolean hasErrors = false;
    for (VariableDeclaration vd: variableDeclarations) {
      if (!classDescriptorMap.containsKey(vd.type)) {
        hasErrors = true;
        Util.printLocationAndError(vd.type.loc, "No such class " + vd.type);
      }
    }
    for (MethodDeclaration md: methodDeclarations) {
      Environment env = new Environment(cd, md.parameters, md.methodBody.variableDeclarations, md.type);

      // type check the statements
      Type actualType = md.methodBody.getType(env, classDescriptorMap);
      if (actualType.isErrorType()) {
        hasErrors = true;
      } else if (!actualType.equals(md.type)) {
        Util.printLocationAndError(md.type.loc, "Expected return type of " + md.type + " for method " + md.identifier + ", instead got " + actualType);
        hasErrors = true;
      }
    }
    return !hasErrors;
  }
}

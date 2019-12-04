package node.expression;

import common.Util;
import common.VariableDeclaration;
import ir3.expression.IdentifierExpression3;
import ir3.expression.NewExpression3;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Identifier;
import node.Program;
import common.Type;
import ir3.statement.AssignmentStatement3;

public class NewExpression extends Expression {

  private Type type;

  public NewExpression(Type type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "new " + type.toString() + "()";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    if (!classDescriptorMap.containsKey(type)) {
      Util.printLocationAndError(type.loc, "Class " + type + " does not exist");
      return Type.ERROR;
    }
    return type;
  }

  @Override
  public IR3Pair toIR3(List<VariableDeclaration> tempDeclarations) {
    Identifier temp = Program.getNewTempId();
    tempDeclarations.add(new VariableDeclaration(type, temp));
    return new IR3Pair(new IdentifierExpression3(temp), List.of(new AssignmentStatement3(temp, new NewExpression3(type))));
  }
}

package node.expression;

import common.VariableDeclaration;
import ir3.expression.IdentifierExpression3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Identifier;
import common.Type;

public class IdentifierExpression extends Expression {

  public Identifier identifier;
  private Type type;

  public IdentifierExpression(Identifier identifier) {
    this.identifier = identifier;
  }

  @Override
  public String toString() {
    return identifier.toString();
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    return type = env.lookupVariableType(identifier);
  }

  @Override
  public IR3Pair toIR3(List<VariableDeclaration> tempDeclarations) {
    if (identifier.isClassField) { // we need to treat this as this.identifier
      return new MemberExpression(new ThisExpression(), identifier, type, identifier.classType).toIR3(tempDeclarations);
    } else {
      return new IR3Pair(new IdentifierExpression3(identifier), new ArrayList<>());
    }
  }
}

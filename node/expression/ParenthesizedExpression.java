package node.expression;

import common.VariableDeclaration;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Type;

public class ParenthesizedExpression extends Expression {

  private Expression expression;

  public ParenthesizedExpression(Expression expression) {

    this.expression = expression;
  }

  @Override
  public String toString() {
    return "(" + expression.toString() + ")";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    return expression.getType(env, classDescriptorMap);
  }

  @Override
  public IR3Pair toIR3(List<VariableDeclaration> tempDeclarations) {
    return expression.toIR3(tempDeclarations); // we simply pass it along
  }
}

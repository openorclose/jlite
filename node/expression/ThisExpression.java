package node.expression;

import common.VariableDeclaration;
import ir3.expression.ThisExpression3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Type;

public class ThisExpression extends Expression {

  @Override
  public String toString() {
    return "this";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    return env.thisType;
  }

  @Override
  public IR3Pair toIR3(List<VariableDeclaration> tempDeclarations) {
    return new IR3Pair(new ThisExpression3(), new ArrayList<>());
  }

}

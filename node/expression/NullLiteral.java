package node.expression;

import common.VariableDeclaration;
import ir3.expression.NullLiteral3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Type;

public class NullLiteral extends Expression {

  @Override
  public String toString() {
    return "null";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    return Type.NULL;
  }
  @Override
  public IR3Pair toIR3(List<VariableDeclaration> tempDeclarations) {
    return new IR3Pair(new NullLiteral3(), new ArrayList<>());
  }
}

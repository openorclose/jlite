package node.expression;

import common.VariableDeclaration;
import ir3.expression.IntLiteral3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Type;

public class IntLiteral extends Expression {

  public final int value;

  public IntLiteral(int value) {

    this.value = value;
  }

  @Override
  public String toString() {
    return Integer.toString(value);
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    return Type.INT;
  }

  @Override
  public IR3Pair toIR3(List<VariableDeclaration> tempDeclarations) {
    return new IR3Pair(new IntLiteral3(value), new ArrayList<>());
  }

  @Override
  public boolean isConstant() {
    return true;
  }
}

package node.expression;

import common.VariableDeclaration;
import ir3.expression.BoolLiteral3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Type;

public class BoolLiteral extends Expression {

  public final boolean value;

  public BoolLiteral(boolean value) {

    this.value = value;
  }

  @Override
  public String toString() {
    return value ? "true" : "false";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    return Type.BOOL;
  }

  @Override
  public IR3Pair toIR3(List<VariableDeclaration> tempDeclarations) {
    return new IR3Pair(new BoolLiteral3(value), new ArrayList<>());
  }

  @Override
  public boolean isConstant() {
    return true;
  }
}

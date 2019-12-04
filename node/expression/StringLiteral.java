package node.expression;

import common.VariableDeclaration;
import ir3.Program3;
import ir3.expression.StringLiteral3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Type;
import node.Program;

public class StringLiteral extends Expression {

  private String value;

  public StringLiteral(String value) {

    this.value = value;
  }

  @Override
  public String toString() {
    return "\"" + value + "\"";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    return Type.STRING;
  }

  @Override
  public IR3Pair toIR3(List<VariableDeclaration> tempDeclarations) {
    Program3.STRING_ADDRESS_MAP.putIfAbsent(value, Program3.STRING_ADDRESS_MAP.size());
    return new IR3Pair(new StringLiteral3(value), new ArrayList<>());
  }

}

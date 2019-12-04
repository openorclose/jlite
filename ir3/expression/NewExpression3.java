package ir3.expression;

import common.Type;
import ir3.CMethod3;

public class NewExpression3 extends Expression3 {

  private Type type;

  public NewExpression3(Type type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "new " + type.toString() + "()";
  }

  @Override
  public String toArm(CMethod3 method) {
    return "mov a1, #" + method.classMap.get(type).size() + "\n" +
            "bl _Znwj(PLT)\n" +
            "mov v1, a1\n";
  }
}

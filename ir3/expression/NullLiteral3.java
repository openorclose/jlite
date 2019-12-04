package ir3.expression;

import ir3.CMethod3;

public class NullLiteral3 extends Expression3 {

  @Override
  public String toString() {
    return "null";
  }

  @Override
  public String toArm(CMethod3 method) {
    // no instruction, we just result in undefined behaviour
    return "";
  }
}

package ir3.expression;

import ir3.CMethod3;

public class ThisExpression3 extends Expression3 {

  @Override
  public String toString() {
    return "this";
  }

  @Override
  public String toArm(CMethod3 method) {
    return "ldr v1, [fp, #-28]\n";
  }
}

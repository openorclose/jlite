package ir3.expression;

import ir3.CMethod3;

public class BoolLiteral3 extends Expression3 {

  public final boolean value;

  public BoolLiteral3(boolean value) {

    this.value = value;
  }

  @Override
  public String toString() {
    return value ? "true" : "false";
  }

  @Override
  public String toArm(CMethod3 method) {
    return "mov v1, #" + (value ? "1" : "0") + "\n";
  }

  @Override
  public boolean isConstant() {
    return true;
  }
}

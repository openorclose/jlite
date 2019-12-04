package ir3.expression;

import ir3.CMethod3;

public class IntLiteral3 extends Expression3 {

  public final int value;

  public IntLiteral3(int value) {

    this.value = value;
  }

  @Override
  public String toString() {
    return Integer.toString(value);
  }

  @Override
  public String toArm(CMethod3 method) {
    return "mov v1, #" + value + "\n";
  }

  @Override
  public boolean isConstant() {
    return true;
  }
}

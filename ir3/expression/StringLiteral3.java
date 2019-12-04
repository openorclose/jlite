package ir3.expression;

import ir3.CMethod3;
import ir3.Program3;

public class StringLiteral3 extends Expression3 {

  private String value;

  public StringLiteral3(String value) {

    this.value = value;
  }

  @Override
  public String toString() {
    return "\"" + value + "\"";
  }

  @Override
  public String toArm(CMethod3 method) {
    return "ldr v1, =.string" + Program3.STRING_ADDRESS_MAP.get(value) + "\n";
  }
}

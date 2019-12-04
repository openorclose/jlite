package ir3.expression;

import common.Identifier;
import ir3.CMethod3;

public class IdentifierExpression3 extends Expression3 {

  public Identifier identifier;
  public boolean v5 = false;

  public IdentifierExpression3(Identifier identifier) {
    this.identifier = identifier;
  }

  @Override
  public String toString() {
    return identifier.toString();
  }

  @Override
  public String toArm(CMethod3 method) {
    if (identifier.isTemp()) {
      return "mov v1, " + (v5 ? "v5" : "v4") +  "\n";
    } else {
      return "ldr v1, [fp, #-" + method.offsetMap.get(identifier) + "]\n";
    }
  }
}

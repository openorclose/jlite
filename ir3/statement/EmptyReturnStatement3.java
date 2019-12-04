package ir3.statement;

import ir3.CMethod3;

public class EmptyReturnStatement3 extends Statement3 {

  @Override
  public String toString() {
    return "Return;";
  }

  @Override
  public String toArm(CMethod3 method) {
    return "b" + method.getReturnLabel() + "\n";
  }
}

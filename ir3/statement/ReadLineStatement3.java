package ir3.statement;

import common.Identifier;
import ir3.CMethod3;

public class ReadLineStatement3 extends Statement3 {

  private Identifier identifier;

  public ReadLineStatement3(Identifier identifier) {
    this.identifier = identifier;
  }

  @Override
  public String toString() {
    return "readln(" + identifier.toString() + ");";
  }

  @Override
  public String toArm(CMethod3 method) {
    return "";
    // no readln, will result in undefined behaviour.
  }
}

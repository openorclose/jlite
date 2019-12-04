package ir3.statement;

import ir3.CMethod3;
import ir3.expression.IdentifierExpression3;

public class ReturnStatement3 extends Statement3 {

  private IdentifierExpression3 identifierExpression3;

  public ReturnStatement3(IdentifierExpression3 identifierExpression3) {
    this.identifierExpression3 = identifierExpression3;
  }

  @Override
  public String toString() {
    return "Return " + identifierExpression3.toString() + ";";
  }

  @Override
  public String toArm(CMethod3 method) {
    return identifierExpression3.toArm(method) + "mov a1, v1\nb " + method.getReturnLabel() + "\n";
  }
}

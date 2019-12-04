package ir3.expression;

import common.Operator.Unary;
import ir3.CMethod3;

public class UnaryExpression3 extends Expression3 {

  private final Unary operator;
  private final Expression3 expression3;

  public UnaryExpression3(Unary operator, Expression3 expression3) {

    this.operator = operator;
    this.expression3 = expression3;
  }

  @Override
  public String toString() {
    return operator.toString()  + expression3.toString();
  }

  @Override
  public String toArm(CMethod3 method) {
    if (operator.equals(Unary.NEGATE)) {
      return expression3.toArm(method) + "neg v1, v1\n";
    } else {
      return expression3.toArm(method) + "eor v1, v1, #1\n";
    }
  }
}

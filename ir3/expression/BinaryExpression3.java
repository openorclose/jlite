package ir3.expression;

import common.Operator.Binary;
import ir3.CMethod3;
import node.expression.IntLiteral;

public class BinaryExpression3 extends Expression3 {

  private final Binary operator;
  private final Expression3 left;
  private final Expression3 right;

  public BinaryExpression3(Binary operator, Expression3 left, Expression3 right) {

    this.operator = operator;
    this.left = left;
    this.right = right;
  }

  @Override
  public String toString() {
    return left.toString() + " " + operator.toString() + " " + right.toString();
  }

  @Override
  public String toArm(CMethod3 method) {
    // if it's a constant, we can eval it first
    String leftAndRight = left.toArm(method) + "mov v2, v1\n" + right.toArm(method) + "mov v3, v1\n";
    String operation = "ERROR";
    switch (operator) {
      case PLUS:
        operation = "add v1, v2, v3\n";
        break;
      case MINUS:
        operation = "sub v1, v2, v3\n";
        break;
      case TIMES:
        operation = "mul v1, v2, v3\n";
        break;
      case LT:
        operation = "cmp v2, v3\n" +
                "movlt v1, #1\n" +
                "movge v1, #0\n";
        break;
      case LTE:
        operation = "cmp v2, v3\n" +
                "movle v1, #1\n" +
                "movgt v1, #0\n";
        break;
      case EQ:
        operation = "cmp v2, v3\n" +
                "moveq v1, #1\n" +
                "movne v1, #0\n";
        break;
      case NEQ:
        operation = "cmp v2, v3\n" +
                "moveq v1, #0\n" +
                "movne v1, #1\n";
        break;
      case GT:
        operation = "cmp v2, v3\n" +
                "movgt v1, #1\n" +
                "movle v1, #0\n";
        break;
      case GTE:
        operation = "cmp v2, v3\n" +
                "movge v1, #1\n" +
                "movlt v1, #0\n";
        break;
      case OR:
        operation = "orr v1, v2, v3\n";
        break;
      case AND:
        operation = "and v1, v2, v3\n";
    }
    return leftAndRight + operation;
  }
}

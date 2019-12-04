package ir3.expression;

import java.util.ArrayList;
import java.util.List;
import common.Util;
import ir3.CMethod3;

public class CallExpression3 extends Expression3 {

  private final IdentifierExpression3 callee;
  private List<Expression3> arguments;

  public CallExpression3(IdentifierExpression3 callee, ArrayList<Expression3> arguments) {
    this.callee = callee;
    this.arguments = arguments;
  }

  @Override
  public String toString() {
    return callee.toString() + "(" + Util.listToString(arguments) + ")";
  }

  @Override
  public String toArm(CMethod3 method) {
    String loadParams = "";
    for (int i = 0; i < arguments.size(); i++) {
      loadParams += arguments.get(i).toArm(method);
      if (i <= 3) {
        loadParams += "mov a" + (i + 1) + ", v1\n";
      } else {
        loadParams += "str v1, [sp, #-4]!\n";
      }
    }
    String result = loadParams + "bl " + callee.identifier + "\n";
    if (arguments.size() > 4) {
      result += "add sp, sp, #" + (arguments.size() - 4) * 4 + "\n";
    }
    return result + "mov v1, a1\n";
  }
}

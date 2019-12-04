package ir3.statement;

import ir3.CMethod3;
import ir3.expression.Expression3;
import common.Identifier;

public class AssignmentStatement3 extends Statement3 {

  private final Identifier identifier;
  private final Expression3 expression3;
  public boolean v5 = false;

  public AssignmentStatement3(Identifier identifier, Expression3 expression3){
    this.identifier = identifier;
    this.expression3 = expression3;
  }

  @Override
  public String toString() {
    return identifier.toString() + " = " + expression3.toString() + ";";
  }

  @Override
  public String toArm(CMethod3 method) {
    if (identifier.isTemp()) {
      return expression3.toArm(method) + "mov " + (v5 ? "v5" : "v4") + ", v1\n";
    } else {
      return expression3.toArm(method) + "str v1, [fp, #-" + method.offsetMap.get(identifier) + "]\n";
    }
  }
}

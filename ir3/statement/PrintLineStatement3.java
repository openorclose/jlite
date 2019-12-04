package ir3.statement;

import common.Type;
import ir3.CMethod3;
import ir3.expression.Expression3;

public class PrintLineStatement3 extends Statement3 {

  private Expression3 expression3;
  private Type type;

  public PrintLineStatement3(Expression3 expression3, Type type){
    this.expression3 = expression3;
    this.type = type;
  }

  @Override
  public String toString() {
    return "println(" + expression3.toString() + ");";
  }

  @Override
  public String toArm(CMethod3 method) {
    if (type.equals(Type.BOOL)) {
      return expression3.toArm(method) + "cmp v1, #1\nldreq a2, =.true\nldrne a2, =.false\nldr a1, =.string_format\nbl printf(PLT)\n";
    }
    String format = type.equals(Type.INT) ? "int_format" : "string_format";
    return expression3.toArm(method) + "mov a2, v1\nldr a1, =." + format + "\n"
            + "bl printf(PLT)\n";
  }
}

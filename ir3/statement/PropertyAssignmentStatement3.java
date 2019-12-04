package ir3.statement;

import common.Type;
import ir3.CMethod3;
import ir3.expression.Expression3;
import common.Identifier;

public class PropertyAssignmentStatement3 extends Statement3 {

  private final Expression3 object;
  private final Identifier property;
  private final Expression3 expression3;
  private Type objectType;

  public PropertyAssignmentStatement3(
          Expression3 object, Identifier property, Expression3 expression3, Type objectType){
    this.object = object;
    this.property = property;
    this.expression3 = expression3;
    this.objectType = objectType;
  }

  @Override
  public String toString() {
    return object.toString() + "." + property.toString() + " = " + expression3.toString() + ";";
  }

  @Override
  public String toArm(CMethod3 method) {
    return expression3.toArm(method) + "mov v2, v1\n" + object.toArm(method) +
            "str v2, [v1, #" + method.classMap.get(objectType).offsetMap.get(property) + "]\n";
  }
}

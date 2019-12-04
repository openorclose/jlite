package ir3.expression;

import common.Identifier;
import common.Type;
import ir3.CMethod3;

public class MemberExpression3 extends Expression3 {

  public final Expression3 object;
  public final Identifier property;
  private final Type objectType;

  public MemberExpression3(Expression3 object, Identifier property, Type objectType){
    this.object = object;
    this.property = property;
    this.objectType = objectType;
  }

  @Override
  public String toString() {
    return object.toString() + "." + property.toString();
  }

  @Override
  public String toArm(CMethod3 method) {
    return object.toArm(method) + "ldr v1, [v1, #" + method.classMap.get(objectType).offsetMap.get(property) + "]\n";
  }
}

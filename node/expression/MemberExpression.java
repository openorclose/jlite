package node.expression;

import common.Util;
import common.VariableDeclaration;
import ir3.expression.Expression3;
import ir3.expression.IdentifierExpression3;
import ir3.expression.MemberExpression3;
import ir3.expression.ThisExpression3;
import ir3.statement.AssignmentStatement3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Identifier;
import node.Program;
import common.Type;

public class MemberExpression extends Expression {

  public final Expression object;
  public final Identifier property;
  public Type type;
  public Type objectType;

  public MemberExpression(Expression object, Identifier property, Type type, Type objectType) {
    this.object = object;
    this.property = property;
    this.type = type;
    this.objectType = objectType;
  }

  public MemberExpression(Expression object, Identifier property){
    this.object = object;
    this.property = property;
  }

  @Override
  public String toString() {
    return "[" + object.toString() + "." + property.toString() + "]";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    Type objectType = object.getType(env, classDescriptorMap);
    if (objectType.isErrorType()) {
      return Type.ERROR;
    }
    this.objectType = objectType;
    Map<Identifier, Type> fields = classDescriptorMap.get(objectType).fields;
    if (!fields.containsKey(property)) {
      Util.printLocationAndError(property.loc, "Field " + property + " does not exist in class " + objectType);
      return Type.ERROR;
    }
    return this.type = fields.get(property);
  }

  @Override
  public IR3Pair toIR3(List<VariableDeclaration> tempDeclarations) {
    ArrayList<AssignmentStatement3> result = new ArrayList<>();
    if (object instanceof IdentifierExpression || object instanceof ThisExpression) {
      // is valid IR3 already, we assign it a tempid and return it
      Identifier temp = Program.getNewTempId();
      Expression3 ir3object = object instanceof ThisExpression ? new ThisExpression3() : new IdentifierExpression3(((IdentifierExpression) object).identifier);
      result.add(new AssignmentStatement3(temp, new MemberExpression3(ir3object, property, objectType)));
      tempDeclarations.add(new VariableDeclaration(this.type, temp));
      return new IR3Pair(new IdentifierExpression3(temp), result);
    }
    IR3Pair objectPair = object.toIR3(tempDeclarations); // else we need to reduce the part left of the `.`
    result.addAll(objectPair.statements);
    Identifier temp = Program.getNewTempId();
    result.add(new AssignmentStatement3(temp, new MemberExpression3(objectPair.idc3, property, objectType)));
    tempDeclarations.add(new VariableDeclaration(this.type ,temp));
    return new IR3Pair(new IdentifierExpression3(temp), result);
  }
}

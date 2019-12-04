package node.statement;

import common.Identifier;
import common.Type;
import common.Util;
import common.VariableDeclaration;
import ir3.expression.Expression3;
import ir3.expression.IdentifierExpression3;
import ir3.expression.ThisExpression3;
import ir3.statement.PropertyAssignmentStatement3;
import ir3.statement.Statement3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import node.expression.Expression;
import node.expression.IdentifierExpression;
import node.expression.ThisExpression;

public class PropertyAssignmentStatement extends Statement {

  private final Expression object;
  private final Identifier property;
  private final Expression expression;
  private Type objectType;

  public PropertyAssignmentStatement(Expression object, Identifier property, Expression expression) {
    this(object, property, expression, null);
  }

  public PropertyAssignmentStatement(Expression object, Identifier property, Expression expression, Type objectType){
    this.object = object;
    this.property = property;
    this.expression = expression;
    this.objectType = objectType;
  }

  @Override
  public String toString() {
    return object.toString() + "." + property.toString() + " = " + expression.toString() + ";";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    Type objectType = object.getType(env, classDescriptorMap);
    if (objectType.isErrorType()) {
      return Type.ERROR;
    }
    this.objectType = objectType;
    Type exprType = expression.getType(env, classDescriptorMap);
    if (exprType.isErrorType()) {
      return Type.ERROR;
    }
    if (!classDescriptorMap.containsKey(objectType)) {
      Util.printLocationAndError(property.loc , "Class " + objectType + " does not exist.");
      return Type.ERROR;
    }

    Map<Identifier, Type> fields = classDescriptorMap.get(objectType).fields;
    if (!fields.containsKey(property)) {
      Util.printLocationAndError(property.loc, "Field " + property + " does not exist in class " + objectType);
      return Type.ERROR;
    }
    Type propertyType = fields.get(property);
    if (propertyType.equals(exprType) || (!propertyType.isPrimitive() && !env.returnType.equals(Type.VOID)  && exprType.equals(Type.NULL))) {
      return Type.VOID;
    } else {
      Util.printLocationAndError(property.loc, objectType + "#" + property + " is of type " + propertyType + ", but trying to assign type " + exprType);
      return Type.ERROR;
    }
  }

  @Override
  public List<Statement3> toIR3(List<VariableDeclaration> tempDeclarations) {
    ArrayList<Statement3> result = new ArrayList<>();
    Expression3 idc3;
    if (object instanceof ThisExpression) {
      idc3 = new ThisExpression3();
    } else {
      IR3Pair objectPair = object.toIR3(tempDeclarations); // if they are not "simple" enough, we need to reduce.
      result.addAll(objectPair.statements);
      idc3 = objectPair.idc3;
    }

    IR3Pair exprPair = expression.toIR3(tempDeclarations);
    result.addAll(exprPair.statements);

    result.add(new PropertyAssignmentStatement3(idc3, property, exprPair.idc3, objectType));
    return result;
  }
}

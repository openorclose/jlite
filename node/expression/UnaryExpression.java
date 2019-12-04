package node.expression;

import common.Location;
import common.Util;
import common.VariableDeclaration;
import ir3.expression.IdentifierExpression3;
import ir3.expression.UnaryExpression3;
import ir3.statement.AssignmentStatement3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Identifier;
import common.Operator.Unary;
import node.Program;
import common.Type;

public class UnaryExpression extends Expression {

  private final Unary operator;
  private final Expression expression;
  private final Location loc;

  private Type type;

  public UnaryExpression(Unary operator, Expression expression) {
    this(operator, expression, null);
  }

  public UnaryExpression(Unary operator, Expression expression, Location loc) {

    this.operator = operator;
    this.expression = expression;
    this.loc = loc;
  }

  @Override
  public String toString() {
    return "(" + operator.toString() + ")[" + expression.toString() + "]";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    Type actualType = expression.getType(env, classDescriptorMap);
    if (actualType.isErrorType()) {
      return Type.ERROR;
    }
    Type expectedType = operator == Unary.NOT ? Type.BOOL : Type.INT;

    if (!actualType.equals(expectedType)) {
      Util.printLocationAndError(loc, "Expected type " + expectedType + " for " + operator + ", instead got type " + actualType);
      return Type.ERROR;
    }
    this.type = expectedType;
    return expectedType;
  }

  public IR3Pair toIR3(List<VariableDeclaration> tempDeclarations) {
    IR3Pair exprPair = expression.toIR3(tempDeclarations);

    ArrayList<AssignmentStatement3> result = new ArrayList<>(exprPair.statements);

    Identifier temp = Program.getNewTempId();
    tempDeclarations.add(new VariableDeclaration(type, temp));
    result.add(new AssignmentStatement3(temp, new UnaryExpression3(operator, exprPair.idc3)));
    return new IR3Pair(new IdentifierExpression3(temp), result);
  }
}

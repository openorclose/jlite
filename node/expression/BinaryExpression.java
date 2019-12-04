package node.expression;

import common.Location;
import common.Util;
import common.VariableDeclaration;
import ir3.expression.BinaryExpression3;
import ir3.expression.IdentifierExpression3;
import ir3.expression.IntLiteral3;
import ir3.statement.AssignmentStatement3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Identifier;
import common.Operator.Binary;
import node.Program;
import common.Type;

public class BinaryExpression extends Expression {

  private final Binary operator;
  public final Expression left;
  public final Expression right;
  private final Location loc;
  private Type type;

  public BinaryExpression(Binary operator, Expression left, Expression right) {
    this(operator, left, right, null);
  }

  public BinaryExpression(Binary operator, Expression left, Expression right,
          Location loc) {

    this.operator = operator;
    this.left = left;
    this.right = right;
    this.loc = loc;
  }

  @Override
  public String toString() {
    return "[" + left.toString() + ", " + right.toString() + "](" + operator.toString() + ")";
  }

  public IR3Pair toIR3(List<VariableDeclaration> tempDeclarations) {
    if (isConstant()) {
      Identifier temp = Program.getNewTempId();
      tempDeclarations.add(new VariableDeclaration(this.type, temp));
      return new IR3Pair(new IdentifierExpression3(temp),
              List.of(new AssignmentStatement3(temp, new IntLiteral3(getConstantValue(this)))));
    }
    IR3Pair leftPair = left.toIR3(tempDeclarations);
    IR3Pair rightPair = right.toIR3(tempDeclarations);
    if (!rightPair.statements.isEmpty()) {
      rightPair.statements.get(rightPair.statements.size() - 1).v5 = true;
    }
    if (rightPair.idc3 instanceof IdentifierExpression3) {
      ((IdentifierExpression3) rightPair.idc3).v5 = true;
    }

    ArrayList<AssignmentStatement3> result = new ArrayList<>(leftPair.statements);
    result.addAll(rightPair.statements);

    Identifier temp = Program.getNewTempId();
    tempDeclarations.add(new VariableDeclaration(this.type, temp));
    result.add(new AssignmentStatement3(temp, new BinaryExpression3(operator, leftPair.idc3, rightPair.idc3)));

    return new IR3Pair(new IdentifierExpression3(temp), result);
  }

  @Override
  public boolean isConstant() {
    return left.isConstant() && right.isConstant();
  }

  private int getConstantValue(Expression expr) {
    if (expr instanceof IntLiteral) {
      return ((IntLiteral) expr).value;
    } else if (expr instanceof BoolLiteral) {
      return ((BoolLiteral) expr).value ? 1 : 0;
    } else { // it must be a binaryexpression
      BinaryExpression be = (BinaryExpression) expr; 
      switch (operator) {
        case PLUS:
          return getConstantValue(be.left) + getConstantValue(be.right);
        case MINUS:
          return getConstantValue(be.left) - getConstantValue(be.right);
        case TIMES:
          return getConstantValue(be.left) * getConstantValue(be.right);
        case LT:
          return getConstantValue(be.left) < getConstantValue(be.right) ? 1 : 0;
        case LTE:
          return getConstantValue(be.left) <= getConstantValue(be.right) ? 1 : 0;
        case EQ:
          return getConstantValue(be.left) == getConstantValue(be.right) ? 1 : 0;
        case NEQ:
          return getConstantValue(be.left) != getConstantValue(be.right) ? 1 : 0;
        case GT:
          return getConstantValue(be.left) > getConstantValue(be.right) ? 1 : 0;
        case GTE:
          return getConstantValue(be.left) >= getConstantValue(be.right) ? 1 : 0;
        case OR:
          return getConstantValue(be.left) | getConstantValue(be.right);
        case AND:
          return getConstantValue(be.left) & getConstantValue(be.right);
      }
      throw new Error("Binary operator " + operator + " not supported.");
    }
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    Type leftType = left.getType(env, classDescriptorMap);
    Type rightType = right.getType(env, classDescriptorMap);
    Type expectedLeftType;
    Type expectedRightType;
    Type resultType;
    switch (operator) {
      case AND:
      case OR:
        expectedLeftType = expectedRightType = resultType = Type.BOOL;
        break;
      case PLUS:
      case MINUS:
      case TIMES:
      case DIVIDE:
        expectedLeftType = expectedRightType = resultType = Type.INT;
        break;
      default: // relational op
        expectedLeftType = expectedRightType = Type.INT;
        resultType = Type.BOOL;
    }
    if (!leftType.equals(expectedLeftType)) {
      if(!leftType.isErrorType()) { // only add error message if it wasn't an error type
        Util.printLocationAndError(loc, "Expected type " + expectedLeftType + " on left of "
                + operator + " instead got type " + leftType);
      }
      resultType = Type.ERROR;
    }
    if (!rightType.equals(expectedRightType)) {
      if(!rightType.isErrorType()) { // only add error message if it wasn't an error type
        Util.printLocationAndError(loc, "Expected type " + expectedRightType + " on right of "
                + operator + " instead got type " + rightType);
      }
      resultType = Type.ERROR;
    }
    this.type = resultType; // save it for ir3 use
    return resultType;
  }
}

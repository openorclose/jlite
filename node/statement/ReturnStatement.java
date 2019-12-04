package node.statement;

import common.Identifier;
import common.Location;
import common.Util;
import common.VariableDeclaration;
import ir3.expression.IdentifierExpression3;
import ir3.statement.AssignmentStatement3;
import ir3.statement.ReturnStatement3;
import ir3.statement.Statement3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Type;
import node.Program;
import node.expression.Expression;

public class ReturnStatement extends Statement {

  private Expression expression;
  private Location loc;
  private Type type;

  public ReturnStatement(Expression expression, Location loc) {
    this.expression = expression;
    this.loc = loc;
  }

  @Override
  public String toString() {
    return "Return " + expression.toString() + ";";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    Type exprType = expression.getType(env, classDescriptorMap);
    if (exprType.isErrorType()) {
      return Type.ERROR;
    } else if (exprType.equals(env.returnType) || (!env.returnType.isPrimitive() && !env.returnType.equals(Type.VOID) && exprType.equals(Type.NULL))) {
      // if return null to nonprimitive, also allow
      return this.type = env.returnType;
    } else {
      Util.printLocationAndError(loc, "Return type expected to be " + env.returnType + ", instead got " + exprType);
      return Type.ERROR;
    }
  }

  @Override
  public List<Statement3> toIR3(List<VariableDeclaration> tempDeclarations) {
    IR3Pair ir3Expr = expression.toIR3(tempDeclarations);
    ArrayList<Statement3> result = new ArrayList<>(ir3Expr.statements);
    if (ir3Expr.idc3 instanceof IdentifierExpression3) { //no need convert
      result.add(new ReturnStatement3((IdentifierExpression3) ir3Expr.idc3));
    } else { // need to store into an id then return
      Identifier temp = Program.getNewTempId();
      tempDeclarations.add(new VariableDeclaration(this.type, temp));
      result.add(new AssignmentStatement3(temp, ir3Expr.idc3));
      result.add(new ReturnStatement3(new IdentifierExpression3(temp)));
    }
    return result;
  }
}

package node.statement;

import common.Util;
import common.VariableDeclaration;
import ir3.statement.AssignmentStatement3;
import ir3.statement.Statement3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Type;
import node.expression.Expression;
import common.Identifier;
import node.expression.ThisExpression;

public class AssignmentStatement extends Statement {

  private final Identifier identifier;
  private final Expression expression;

  public AssignmentStatement(Identifier identifier, Expression expression){
    this.identifier = identifier;
    this.expression = expression;
  }

  @Override
  public String toString() {
    return identifier.toString() + " = " + expression.toString() + ";";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    Type idType = env.lookupVariableType(identifier);
    Type exprType = expression.getType(env, classDescriptorMap);
    if (idType.isErrorType() || exprType.isErrorType()) {
      return Type.ERROR;
    }
    if (idType.equals(exprType) || (exprType.equals(Type.NULL) && !env.returnType.equals(Type.VOID)  && !idType.isPrimitive())) { // if not a primitive var, and trying to assign null, we allow.
      return Type.VOID;
    }
    Util.printLocationAndError(identifier.loc, "Trying to assign type " + exprType + " to type " + idType + " of "
                    + identifier);
    return Type.ERROR;
  }

  @Override
  public List<Statement3> toIR3(List<VariableDeclaration> tempDeclarations) {
    if (identifier.isClassField) { // we need to treat this as this.identifier = ...
      return new PropertyAssignmentStatement(new ThisExpression(), identifier, expression, identifier.classType).toIR3(tempDeclarations);
    } else {
      IR3Pair exprPair = expression.toIR3(tempDeclarations);
      ArrayList<Statement3> result = new ArrayList<>(exprPair.statements);
      result.add(new AssignmentStatement3(identifier, exprPair.idc3));
      return result;
    }
  }
}

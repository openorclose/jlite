package node.statement;

import common.VariableDeclaration;
import ir3.statement.Statement3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import common.Type;
import node.expression.CallExpression;

public class CallStatement extends Statement {

  private CallExpression callExpression;

  public CallStatement(CallExpression callExpression) {
    this.callExpression = callExpression;
  }

  @Override
  public String toString() {
    return callExpression.toString() + ";";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    Type type = callExpression.getType(env, classDescriptorMap);
    return type.isErrorType() ? Type.ERROR : Type.VOID;
  }

  @Override
  public List<Statement3> toIR3(List<VariableDeclaration> tempDeclarations) {
    return new ArrayList<>(callExpression.toIR3(tempDeclarations).statements);
  }
}

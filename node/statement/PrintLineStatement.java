package node.statement;

import common.Location;
import common.Util;
import common.VariableDeclaration;
import ir3.statement.PrintLineStatement3;
import ir3.statement.Statement3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import common.Type;
import node.expression.Expression;

public class PrintLineStatement extends Statement {

  private Expression expression;
  private Location loc;
  private Type type;

  public PrintLineStatement(Expression expression, Location loc){
    this.expression = expression;
    this.loc = loc;
  }

  @Override
  public String toString() {
    return "Println(" + expression.toString() + ");";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    Type exprType = expression.getType(env, classDescriptorMap);
    this.type = exprType;
    if (exprType.isPrimitive()) {
      return Type.VOID;
    } else if (exprType.isErrorType()) {
      return Type.ERROR;
    }
    Util.printLocationAndError(loc, "Println expects an Int, Bool, or String, instead got: " + exprType);
    return Type.ERROR;
  }

  @Override
  public List<Statement3> toIR3(List<VariableDeclaration> tempDeclarations) {
    IR3Pair exprPair = expression.toIR3(tempDeclarations);
    ArrayList<Statement3> result = new ArrayList<>(exprPair.statements);
    result.add(new PrintLineStatement3(exprPair.idc3, type));
    return result;
  }
}

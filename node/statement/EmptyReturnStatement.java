package node.statement;

import common.Location;
import common.Util;
import common.VariableDeclaration;
import ir3.statement.EmptyReturnStatement3;
import ir3.statement.Statement3;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import common.Type;

public class EmptyReturnStatement extends Statement {

  private Location loc;

  public EmptyReturnStatement(Location loc) {
    this.loc = loc;
  }

  @Override
  public String toString() {
    return "Return;";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    if (!env.returnType.equals(Type.VOID)) {
      Util.printLocationAndError(loc, "Return type expected to be " + env.returnType + ", instead got Void");
      return Type.ERROR;
    }
    return Type.VOID;
  }

  @Override
  public List<Statement3> toIR3(List<VariableDeclaration> tempDeclarations) {
    return List.of(new EmptyReturnStatement3());
  }
}

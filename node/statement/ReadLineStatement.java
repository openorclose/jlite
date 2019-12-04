package node.statement;

import common.Util;
import common.VariableDeclaration;
import ir3.statement.ReadLineStatement3;
import ir3.statement.Statement3;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import common.Identifier;
import common.Type;

public class ReadLineStatement extends Statement {

  private Identifier identifier;

  public ReadLineStatement(Identifier identifier) {
    this.identifier = identifier;
  }

  @Override
  public String toString() {
    return "Readln(" + identifier.toString() + ");";
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    Type idType = env.lookupVariableType(identifier);
    if (idType.isPrimitive()) {
      return Type.VOID;
    } else if (idType.isErrorType()) {
      return Type.ERROR;
    }
    Util.printLocationAndError(idType.loc, "Readln expects an Int, Bool, or String, instead got: " + idType);
    return Type.ERROR;
  }

  @Override
  public List<Statement3> toIR3(List<VariableDeclaration> tempDeclarations) {
    return List.of(new ReadLineStatement3(identifier));
  }
}

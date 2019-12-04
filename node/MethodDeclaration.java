package node;

import common.Identifier;
import common.Parameter;
import common.Type;
import common.Util;
import java.util.ArrayList;

public class MethodDeclaration implements Node {

  public final Type type;
  public final Identifier identifier;
  public final ArrayList<Parameter> parameters;
  public final MethodBody methodBody;
  public int methodNumber = -1;

  public MethodDeclaration(Type type, Identifier identifier, ArrayList<Parameter> parameters, MethodBody methodBody) {

    this.type = type;
    this.identifier = identifier;
    this.parameters = parameters;
    this.methodBody = methodBody;
  }

  @Override
  public String toString() {
    return String.format("%s %s(%s) {\n%s}", type.toString(), identifier.toString(), Util.listToString(parameters),
        methodBody.toString());
  }
}

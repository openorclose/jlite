package node;

import common.Parameter;
import common.Type;
import common.Util;
import java.util.ArrayList;

public class MainClass implements Node {

  public Type type;
  public final ArrayList<Parameter> parameters;
  public final MethodBody body;

  public MainClass(Type type, ArrayList<Parameter> parameters, MethodBody body) {
    this.type = type;
    this.parameters = parameters;
    this.body = body;
  }

  @Override
  public String toString() {
    return String.format("class %s {\n\tvoid main(%s) {\n%s\t}\n}", type, Util.listToString(parameters), Util.indent(body.toString()));
  }
}

package common;

import node.Node;

public class VariableDeclaration implements Node {

  public final Type type;
  public final Identifier identifier;

  public VariableDeclaration(Type type, Identifier identifier) {

    this.type = type;
    this.identifier = identifier;
  }

  @Override
  public String toString() {
    return type.toString() + " " + identifier + ";";
  }
}

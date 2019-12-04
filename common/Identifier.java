package common;

import node.Node;

public class Identifier implements Node {

  private String name;
  public Location loc = new Location(-1, -1);
  public boolean isClassField = false; // used for codegen
  public Type classType = null;
  public static final Identifier THIS = new Identifier("this"); // to represent first parameter of IR3

  public Identifier(String name, Location loc) {
    this.name = name;
    this.loc = loc;
  }

  public Identifier(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

  public boolean isTemp() {
    return false;
  }

  public static Identifier ERROR() {
    return new Identifier("!!!IDENTIFIER ERROR!!!");
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    } else if (o instanceof Identifier) {
      return ((Identifier) o).name.equals(name);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

}

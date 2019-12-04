package common;

import node.Node;

public class Type implements Node {

  public String name;
  public static Type INT = new Type("Int");
  public static Type BOOL = new Type("Bool");
  public static Type STRING = new Type("String");
  public static Type VOID = new Type("Void");

  // below are some internal types used. since their names start with lowercase letter, it can't clash with actual types.
  public static Type NULL = new Type("null"); // internal null type reference
  public static Type ERROR = new Type("error"); // internal error type used for type checking
  public Location loc = new Location(-1, -1);

  public Type(String name, Location loc) {
    this.name = name;
    this.loc = loc;
  }

  public Type(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    } else if (o instanceof Type) {
      return ((Type) o).name.equals(name);
    } else {
      return false;
    }
  }

  public boolean isErrorType() {
    return equals(ERROR);
  }

  public boolean isPrimitive() {
    return equals(INT) || equals(STRING) || equals(BOOL);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }


}

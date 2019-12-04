package common;

public class Parameter {

  public final Type type;
  public final Identifier identifier;

  public Parameter(Type type, Identifier identifier) {

    this.type = type;
    this.identifier = identifier;
  }

  @Override
  public String toString() {
    return type.toString() + " " + identifier;
  }
}

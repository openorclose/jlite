package node;

import common.Identifier;
import common.Type;
import common.Util;
import common.VariableDeclaration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import node.statement.Statement;
import node.statement.StatementList;

public class MethodBody implements Node {

  public final List<VariableDeclaration> variableDeclarations;
  public final StatementList statements;

  public MethodBody(List<VariableDeclaration> variableDeclarations, List<Statement> statements){
    this.variableDeclarations = variableDeclarations;
    this.statements = new StatementList(statements);
  }

  @Override
  public String toString() {
    return Util.indent(Util.joinWithNewLine(variableDeclarations)) + Util.indent(Util.joinWithNewLine(statements.statements));
  }

  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    return statements.getType(env, classDescriptorMap);
  }

}

package node.statement;

import common.Location;
import common.VariableDeclaration;
import ir3.statement.GotoStatement3;
import ir3.statement.IfGotoStatement;
import ir3.statement.LabelStatement3;
import ir3.statement.Statement3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import node.Program;
import common.Type;
import common.Util;
import node.expression.Expression;

public class WhileStatement extends Statement {

  private final Expression test;
  private final StatementList body;
  private Location loc;

  public WhileStatement(Expression test, ArrayList<Statement> body, Location loc) {
    this.test = test;
    this.body = new StatementList(body);
    this.loc = loc;
  }

  public WhileStatement(Expression test, ArrayList<Statement> body) {
    this.test = test;
    this.body = new StatementList(body);
  }

  @Override
  public String toString() {
    return String.format("While (%s) {\n%s}", test.toString(), Util.indent(Util.joinWithNewLine(body.statements)));
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    Type testType = test.getType(env, classDescriptorMap);
    Type bodyType = body.getType(env, classDescriptorMap);
    if (testType.isErrorType()) {
      return Type.ERROR;
    } else if (!testType.equals(Type.BOOL)) {
      Util.printLocationAndError(loc, "Test condition for while statement must be of type Bool, instead got: " + testType);
      return Type.ERROR;
    }
    return bodyType;
  }

  @Override
  public List<Statement3> toIR3(List<VariableDeclaration> tempDeclarations) {
    ArrayList<Statement3> result = new ArrayList<>();
    int testLabel = Program.getNewLabel();
    result.add(new LabelStatement3(testLabel)); // add start of the test condition;
    int bodyLabel = Program.getNewLabel();
    int endLabel = Program.getNewLabel();
    IR3Pair testPair = test.toIR3(tempDeclarations);
    result.addAll(testPair.statements);
    result.add(new IfGotoStatement(testPair.idc3, new GotoStatement3(bodyLabel))); // if true, go to body
    result.add(new GotoStatement3(endLabel)); // else, exit loop
    result.add(new LabelStatement3(bodyLabel)); // start of loop
    result.addAll(body.toIR3(tempDeclarations)); // add all body statements
    result.add(new GotoStatement3(testLabel)); // go back to the test
    result.add(new LabelStatement3(endLabel)); // end of the while loop
    return result;
  }
}

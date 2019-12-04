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

public class IfStatement extends Statement {

  private final Expression test;
  private final StatementList consequent;
  private final StatementList alternate;
  private Location loc;

  public IfStatement(Expression test, ArrayList<Statement> consequent, ArrayList<Statement> alternate, Location loc) {
    this.test = test;
    this.consequent = new StatementList(consequent);
    this.alternate = new StatementList(alternate);
    this.loc = loc;
  }

  public IfStatement(Expression test, ArrayList<Statement> consequent, ArrayList<Statement> alternate) {
    this(test, consequent, alternate, null);
  }

  @Override
  public String toString() {
    return String.format("If (%s) {\n%s} else {\n%s}", test.toString(),
        Util.indent(Util.joinWithNewLine(consequent.statements)), Util.indent(Util.joinWithNewLine(alternate.statements)));
  }

  @Override
  public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
    Type testType = test.getType(env, classDescriptorMap);
    boolean hasError = false;
    if (testType.isErrorType()) {
      hasError = true;
    } else  if (!testType.equals(Type.BOOL)) {
      Util.printLocationAndError(loc, "Test condition for if statement must be Bool, instead got type: " + testType);
      hasError = true;
    }
    Type consequentType = consequent.getType(env, classDescriptorMap);
    Type alternateType = alternate.getType(env, classDescriptorMap);
    if (consequentType.isErrorType() || alternateType.isErrorType()) {
      hasError = true;
    } else  if (!consequentType.equals(alternateType)) {
      Util.printLocationAndError(loc, "Consequent and alternate block of If statement do not give rise to same type:\n" +
              "\t\t\tConsequent type: " + consequentType + "\n\t\t\tAlternate type: " + alternateType);
      hasError = true;
    }
    return hasError ? Type.ERROR : consequentType;
  }

  @Override
  public List<Statement3> toIR3(List<VariableDeclaration> tempDeclarations) {
    int ifGoto = Program.getNewLabel();
    int elseGoto = Program.getNewLabel();
    IR3Pair testPair = test.toIR3(tempDeclarations);
    ArrayList<Statement3> result = new ArrayList<>(testPair.statements);
    result.add(new IfGotoStatement(testPair.idc3, new GotoStatement3(ifGoto)));
    // else block
    result.addAll(alternate.toIR3(tempDeclarations));
    // after else, goto end of block
    result.add(new GotoStatement3(elseGoto));
    // label for the if goto
    result.add(new LabelStatement3(ifGoto));
    result.addAll(consequent.toIR3(tempDeclarations));
    result.add(new LabelStatement3(elseGoto));
    return result;
  }
}

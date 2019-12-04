package ir3.statement;


import ir3.CMethod3;
import ir3.expression.Expression3;

public class IfGotoStatement extends Statement3 {
    public final Expression3 test;
    public final GotoStatement3 gotoStatement3;

    public IfGotoStatement(Expression3 test, GotoStatement3 gotoStatement3) {
        this.test = test;
        this.gotoStatement3 = gotoStatement3;
    }

    @Override
    public String toString() {
        return "If (" + test + ") " + gotoStatement3;
    }

    @Override
    public String toArm(CMethod3 method) {
        return test.toArm(method) + "cmp v1, #1\n" + "beq " + gotoStatement3.getArmLabel() + "\n";
    }
}

package ir3.statement;

import ir3.CMethod3;

public class GotoStatement3 extends Statement3 {

    public int label;

    public GotoStatement3(int label) {

        this.label = label;
    }

    @Override
    public String toString() {
        return "goto " + label + ";";
    }

    public String getArmLabel() {
        return ".L" + label;
    }

    @Override
    public String toArm(CMethod3 method) {
        return "b " + getArmLabel() + "\n";
    }
}

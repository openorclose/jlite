package ir3.statement;

import ir3.CMethod3;

public class LabelStatement3 extends Statement3 {

    private int number;

    public LabelStatement3(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Label " + number + ":";
    }

    public String getArmLabel() {
        return ".L" + number;
    }

    @Override
    public String toArm(CMethod3 method) {
        return getArmLabel() + ":\n";
    }
}

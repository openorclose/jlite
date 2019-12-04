package ir3;

import ir3.expression.Expression3;
import java.util.List;
import ir3.statement.AssignmentStatement3;

public class IR3Pair extends Pair<Expression3, List<AssignmentStatement3>>{

    public final Expression3 idc3;
    public final List<AssignmentStatement3> statements;

    public IR3Pair(Expression3 idc3, List<AssignmentStatement3> statements) {
        super(idc3, statements);
        this.idc3 = this.head;
        this.statements = this.tail;
    }

}

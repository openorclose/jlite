package node.statement;

import common.VariableDeclaration;
import ir3.statement.Statement3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import node.Node;
import common.Type;

public class StatementList implements Node {

    public List<Statement> statements;

    public StatementList(List<Statement> statements) {

        this.statements = statements;
    }

    public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
        Type lastType = Type.VOID;
        boolean hasError = false;
        for (Statement statement: statements) {
            lastType = statement.getType(env, classDescriptorMap);
            if (lastType.isErrorType()) {
                hasError = true;
            }
        }
        return hasError ? Type.ERROR : lastType;
    }

    public List<Statement3> toIR3(List<VariableDeclaration> tempDeclarations) {
        ArrayList<Statement3> result = new ArrayList<>();
        for (Statement s: statements) {
            result.addAll(s.toIR3(tempDeclarations));
        }
        return result;
    }
}

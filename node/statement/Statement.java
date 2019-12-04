package node.statement;

import common.VariableDeclaration;
import ir3.statement.Statement3;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import node.Node;
import common.Type;

public abstract class Statement implements Node {
    public abstract Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap);

    public abstract List<Statement3> toIR3(
            List<VariableDeclaration> tempDeclarations);
}

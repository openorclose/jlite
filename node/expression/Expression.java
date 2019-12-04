package node.expression;

import common.VariableDeclaration;
import java.util.List;
import java.util.Map;
import node.ClassDescriptor;
import node.Environment;
import ir3.IR3Pair;
import node.Node;
import common.Type;

public abstract class Expression implements Node {
    private static class ErrorExpression extends Expression {
        @Override
        public String toString() {
            return "[!!!EXPRESSION ERROR!!!]";
        }

        @Override
        public Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap) {
            return null;
        }

        @Override
        public IR3Pair toIR3(List<VariableDeclaration> tempDeclarations) {
            return null;
        }
    }

    public static Expression ERROR() {
        return new ErrorExpression();
    }

    public abstract Type getType(Environment env, Map<Type, ClassDescriptor> classDescriptorMap);

    public abstract IR3Pair toIR3(List<VariableDeclaration> tempDeclarations);

    public boolean isConstant() {
        return false;
    }

}

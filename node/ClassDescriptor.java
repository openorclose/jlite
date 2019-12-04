package node;

import common.Identifier;
import common.Type;
import java.util.HashMap;
import java.util.Map;

public class ClassDescriptor {
    public Type className;
    public Map<Identifier, Type> fields = new HashMap<>();
    public Map<MethodSignature, MethodDeclaration> methods = new HashMap<>();

    public ClassDescriptor(Type className) {
        this.className = className;
    }
}

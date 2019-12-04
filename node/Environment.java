package node;

import common.Identifier;
import common.Parameter;
import common.Type;
import common.Util;
import common.VariableDeclaration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Environment {
    private final ClassDescriptor parent;
    private final Map<Identifier, Type> parameterMap = new HashMap<>();
    private final Map<Identifier, Type> localsMap = new HashMap<>();
    public final Type thisType;
    public final Type returnType;

    public Environment(ClassDescriptor classDescriptor, List<Parameter> parameters, List<VariableDeclaration> variableDeclarations, Type returnType) {
        parent = classDescriptor;
        for (Parameter p: parameters) {
            parameterMap.put(p.identifier, p.type);
        }
        for (VariableDeclaration vd: variableDeclarations) {
            localsMap.put(vd.identifier, vd.type);
        }
        thisType = parent.className;
        this.returnType = returnType;
    }

    public Type lookupVariableType(Identifier id) {
        Type result;
        if (localsMap.containsKey(id)) {
            // first we look in the method body
            result = localsMap.get(id);
        } else if (parameterMap.containsKey(id)) {
            // if not, we look in params
            result = parameterMap.get(id);
        } else if (parent.fields.containsKey(id)) {
            // else look up the class fields
            // if it is a class field, we must prepend a `this.` during code gen
            id.isClassField = true;
            id.classType = parent.className;
            result = parent.fields.get(id);
        } else {
            // else it can't be found
            result = Type.ERROR;
        }
        if (result.isErrorType()) {
            Util.printLocationAndError(id.loc, "Identifier " + id + " not found!");
        }
        return result;
    }
}

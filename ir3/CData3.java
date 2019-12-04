package ir3;

import common.Identifier;
import common.Type;
import common.Util;
import common.VariableDeclaration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CData3 {

    private final Type type;
    private final List<VariableDeclaration> varDecls;
    public final Map<Identifier, Integer> offsetMap = new HashMap<>();

    public CData3(Type type, List<VariableDeclaration> varDecls) {
        this.type = type;
        this.varDecls = varDecls;
        for (int i = 0; i < varDecls.size(); i++) {
            offsetMap.put(varDecls.get(i).identifier, i * 4);
        }
    }

    @Override
    public String toString() {
        return String.format("class %s {\n%s}\n", type, Util.indent(Util.joinWithNewLine(varDecls)));
    }

    public int size() {
        int size = 0;
        for (VariableDeclaration varDecl: varDecls) {
            if (varDecl.type.equals(Type.BOOL)) {
                size += 4;
            } else if (varDecl.type.equals(Type.INT)) {
                size += 4;
            } else if (varDecl.type.equals(Type.STRING)) {
                size += 4;
            } else {
                size += 4;
            }
        }
        return size;
    }
}

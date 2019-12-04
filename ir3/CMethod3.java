package ir3;

import ir3.statement.LabelStatement3;
import ir3.statement.Statement3;

import java.util.HashMap;
import java.util.List;
import common.Identifier;
import common.Parameter;
import common.Type;
import common.Util;
import common.VariableDeclaration;

import java.util.Map;
import java.util.stream.Collectors;

public class CMethod3 {
    public final Type type;
    public final Identifier identifier;
    public final List<Parameter> parameters;
    public final List<VariableDeclaration> variableDeclarations;
    public final List<Statement3> statements;
    public final Map<Identifier, Integer> offsetMap = new HashMap<>();
    public Map<Type, CData3> classMap;

    public CMethod3(Type type, Identifier identifier, List<Parameter> parameters,
                    List<VariableDeclaration> variableDeclarations,
                    List<Statement3> statements, Map<Type, CData3> classMap) {
        this.type = type;
        this.identifier = identifier;
        this.parameters = parameters;
        this.variableDeclarations = variableDeclarations;
        this.statements = statements;
        this.classMap = classMap;
    }

    @Override
    public String toString() {
        List<String> indentedNonLabelStatements = statements.stream().map(s -> (s instanceof LabelStatement3) ? s.toString() : "  " + s.toString()).collect(
                Collectors.toList());
        List<String> indentedVariableDeclarationStatements = variableDeclarations.stream().map(s -> "  " + s.toString()).collect(
                Collectors.toList());
        return String.format("%s %s(%s) {\n%s\n%s}", type.toString(), identifier.toString(), Util.listToString(parameters),
                Util.indent(Util.joinWithNewLine(indentedVariableDeclarationStatements)), Util.indent(Util.joinWithNewLine(indentedNonLabelStatements)));
    }

    public String toArm() {
        int offset = 24;
        String loadParams = "";
        for (int i = 0; i < parameters.size(); i++) {
            if (i <= 3) {
                loadParams += "str a" + (i + 1) + ", [fp, #-" + (offset += 4) + "]\n";
            } else {
                loadParams += "ldr v1, [fp, #" + (parameters.size() - i) * 4 + "]\n" +
                        "str v1, [fp, #-" + (offset += 4) + "]\n";
            }
            offsetMap.put(parameters.get(i).identifier, offset);
        }
        for (VariableDeclaration vd: variableDeclarations) {
            if (!vd.identifier.isTemp()) {
                offsetMap.put(vd.identifier, offset += 4);
            }
        }

        // prologue:
        String arm = "stmfd sp!, {fp,lr,v1,v2,v3,v4,v5}\n" +
                "add fp, sp, #24\n" +
                "sub sp, fp, #" + offset + "\n";
        arm += loadParams;
        for (Statement3 statement3: statements) {
            arm += (statement3.toArm(this));
        }

        String epilogue = "sub sp,fp,#24\n" +
                "ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}\n";
        return identifier + ":\n" + Util.indent(arm) + getReturnLabel() + ":\n" + Util.indent(epilogue);
    }

    public String getReturnLabel() {
        return identifier + "exit";
    }
}

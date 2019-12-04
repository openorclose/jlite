package ir3;

import common.Util;
import node.Program;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program3 {

    public final List<CData3> datas;
    public final List<CMethod3> methods;

    public static Map<String, Integer> STRING_ADDRESS_MAP = new HashMap<>();

    public Program3(List<CData3> datas, List<CMethod3> methods) {
        this.datas = datas;
        this.methods = methods;
    }

    @Override
    public String toString() {
        return Util.joinWithNewLine(datas) + "\n" + Util.joinWithNewLine(methods);
    }

    public void toArm(boolean optimise) {
        System.out.println(".data");
        System.out.println(".int_format:\n\t.asciz \"%i\\n\"");
        System.out.println(".string_format:\n\t.asciz \"%s\\n\"");
        System.out.println(".true:\n\t.asciz \"true\"");
        System.out.println(".false:\n\t.asciz \"false\"");
        STRING_ADDRESS_MAP.forEach((value, index) -> {
            System.out.print(".string" + index + ":\n\t.asciz ");
            StringBuilder builder = new StringBuilder();
            builder.append("\"");
            for (char c : value.toCharArray()) {
                if (c == '\'')
                    builder.append("\\'");
                else if (c == '\"')
                    builder.append("\\\"");
                else if (c == '\r')
                    builder.append("\\r");
                else if (c == '\n')
                    builder.append("\\n");
                else if (c == '\t')
                    builder.append("\\t");
                else if (c < 32 || c >= 127)
                    builder.append(String.format("\\u%04x", (int) c));
                else
                    builder.append(c);
            }
            builder.append("\"");
            System.out.println(builder.toString());
        });
        System.out.println("\t.text\n" +
                "\t.global main");
        methods.stream().map(CMethod3::toArm).forEach(arm -> {
            String previous = "";
            while (optimise && !arm.equals(previous)) {
                previous = arm;
                /*
                       translates
                       ldr r1, [add]
                       mov r2, r1

                       into

                       ldr r2, [add]
                 */
                arm = arm.replaceAll("ldr (\\w\\w), (.*)(\\s+)mov (\\w\\w), \\1", "ldr $4, $2");
                /*
                       translates
                       mov r1, xxx
                       mov r2, r1

                       into

                       mov r2, xxx
                 */
                arm = arm.replaceAll("mov (\\w\\w), (.*)(\\s+)mov (\\w\\w), \\1", "mov $4, $2");
                /*
                       translates
                       mov r1, r2
                       mov r3, r1

                       into

                       mov r3, r2
                 */
                arm = arm.replaceAll("mov (\\w\\w), (.*)(\\s+)mov (\\w\\w), \\1", "mov $4, $2");
                /*
                       translates
                       str r1, [add]
                       ldr r1, [add]

                       into

                       str r1, [add]
                 */
                arm = arm.replaceAll("str (\\w\\w), (.*)(\\s+)ldr \\1, \\2", "str $1, $2");
                /*
                       translates
                       str r1, [add]
                       ldr r2, [add]

                       into

                       str r1, [add]
                       mov r2, r1
                 */
                arm = arm.replaceAll("str (\\w\\w), (.*)(\\s+)ldr (\\w\\w), \\2", "str $1, $2$3mov $4, $1");
                /*
                       translates
                       op v1, v2, v3
                       mov v2, v1

                       into

                       op v2, v2, v3

                       this can be done since we only use v1 v2 and v3 as temporaries.
                 */
                arm = arm.replaceAll("(\\w+) v1, v2, v3(\\s+)mov v2, v1", "$1 v2, v2, v3");
                /*
                       deletes redundant
                       mov r0, r0
                 */
                arm = arm.replaceAll("mov (\\w\\w), \\1(\\s+)", "");
                /*
                       since v1 is a temp,

                       op v1, r, r
                       mov r, v1

                       can be replaced by

                       op r, r, r
                 */
                arm = arm.replaceAll("(\\w+) v1, (\\w\\w), (\\w\\w)(\\s+)mov (\\w\\w), v1", "$1 $5, $2, $3");
                /*
                       since v4 and 3 are temp,

                       mov v3, v4
                       op r, r, v3

                       can be replaced by

                       op r, r, v4
                 */
                arm = arm.replaceAll("mov v3, v4(\\s+)(\\w+) (\\w\\w), (\\w\\w), v3", "$2 $3, $4, v3");
            }
            System.out.println(arm);
        });
    }
}

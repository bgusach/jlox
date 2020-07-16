package com.craftinginterpreters.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GenerateAst {
    public static void main(String... args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output-directory>");
            System.exit(64);
        }

        var outputDir = args[0];
        defineExpressions(outputDir);
        defineVisitorInterface(outputDir);
    }

    private static void defineExpressions(String outputDir) throws IOException {

        for (var exprDescriptor : expressions) {
            var segments = splitStrip(exprDescriptor, ":");
            var className = segments[0];
            var path = outputDir + "/" + className + ".java";

            System.out.println(path);
            var writer = new PrintWriter(path, "UTF-8");

            writer.println("package com.craftinginterpreters.lox;");
            writer.println();
            writer.println("import java.util.List;");
            writer.println();
            writer.println(String.format("class %s extends Expr {", className));
            writer.println();
            var rawAttrs = segments[1];
            var typedAttrs = splitStrip(rawAttrs, ",");

            // Attr declaration
            for (var typedAttr : typedAttrs) {
                writer.println(String.format("    %s;", typedAttr));
            }

            writer.println();

            // Constructor
            writer.println(String.format("    %s(%s) {", className, rawAttrs));
            // Attach variables to this
            for (var typedAttr : typedAttrs) {
                var varName = splitStrip(typedAttr, " ")[1];
                writer.println(String.format("        this.%1$s = %1$s;", varName));
            }
            writer.println("    }");
            writer.println();

            // accept method
            writer.println("    @Override");
            writer.println("    <R> R accept(Visitor<R> visitor) {");
            writer.println(String.format(
                           "        return visitor.visit%s(this);", className));
            writer.println("    }");

            writer.println("}");
            writer.close();
        }

    }

    private static void defineVisitorInterface(String outputDir) throws IOException {
        var path = outputDir + "/Visitor.java";
        var writer = new PrintWriter(path, "UTF-8");

        writer.println("package com.craftinginterpreters.lox;");
        writer.println();
        writer.println("interface Visitor<R> {");

        for (var classDef : expressions) {
            var className = splitStrip(classDef, ":")[0];
            writer.println(String.format("    R visit%1$s(%1$s %2$s);", className, className.toLowerCase()));
        }

        writer.println("}");
        writer.close();

    }

    private static String[] splitStrip(String input, String delimiter) {
        var segments = input.split(delimiter);

        for (var i = 0; i < segments.length; i++) {
            segments[i] = segments[i].trim();
        }

        return segments;
    }

    private static List<String> expressions = List.of(
            "Binary: Expr left, Token operator, Expr right",
            "Grouping: Expr expression",
            "Literal: Object value",
            "Unary: Token operator, Expr right"
    );
}

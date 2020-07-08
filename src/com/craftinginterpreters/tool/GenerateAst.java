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
        defineAst(outputDir);
    }

    private static void defineAst(String outputDir) throws IOException {

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
            writer.println("class " + className + " extends Expr {");
            writer.println();
            var rawAttrs = segments[1];
            var typedAttrs = splitStrip(rawAttrs, ",");

            // Attr declaration
            for (var typedAttr : typedAttrs) {
                writer.println("    " + typedAttr + ";");
            }

            writer.println();

            // Constructor
            writer.println("    " + className + "(" + rawAttrs + ") {");


            // Attach variables to this
            for (var typedAttr : typedAttrs) {
                var varName = splitStrip(typedAttr, " ")[1];
                writer.println("        this." + varName + " = " + varName + ";");
            }

            writer.println("    }");
            writer.println("}");
            writer.close();
        }

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
            "Literal: Token operator, Expr right",
            "Unary: Token operator, Expr right"
    );
}

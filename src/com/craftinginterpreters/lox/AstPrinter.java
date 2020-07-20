package com.craftinginterpreters.lox;


class AstPrinter implements Visitor<String> {

    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinary(Binary binary) {
        return parenthesize(binary.operator.lexeme, binary.left, binary.right);
    }

    @Override
    public String visitGrouping(Grouping grouping) {
        return parenthesize("group", grouping.expression);
    }

    private String parenthesize(String name, Expr... exprs) {
        var builder = new StringBuilder();

        builder.append("(");
        builder.append(name);
        for (var expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }

    @Override
    public String visitLiteral(Literal literal) {
        var val = literal.value;
        return val == null ? "nil" : val.toString();
    }

    @Override
    public String visitUnary(Unary unary) {
        return parenthesize(unary.operator.lexeme, unary.right);
    }
}

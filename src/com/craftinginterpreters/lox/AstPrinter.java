package com.craftinginterpreters.lox;

class AstPrinter implements Visitor<String> {

    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinary(Binary binary) {
        return null;
    }

    @Override
    public String visitGrouping(Grouping grouping) {
        return null;
    }

    @Override
    public String visitLiteral(Literal literal) {
        return null;
    }

    @Override
    public String visitUnary(Unary unary) {
        return null;
    }
}

package com.craftinginterpreters.lox;

public class RpnPrinter implements Visitor<String> {

    @Override
    public String visitBinary(Binary binary) {
        return String.format(
                "%s %s %s",
                binary.left.accept(this),
                binary.right.accept(this),
                binary.operator.lexeme
        );
    }

    @Override
    public String visitGrouping(Grouping grouping) {
        return grouping.expression.accept(this);
    }

    @Override
    public String visitLiteral(Literal literal) {
        if (literal.value == null) return "nil";

        return literal.value.toString();
    }

    @Override
    public String visitUnary(Unary unary) {
        if (unary.operator.lexeme.equals("-")) {
            return String.format("%s neg", unary.right.accept(this));
        }

        return String.format("%s !", unary.right.accept(this));
    }

    public static void main(String... args) {
        var expression = new Binary(
                new Unary(
                        new Token(TokenType.MINUS, "-", null, 1),
                        new Literal(123)),
                new Token(TokenType.STAR, "*", null, 1),
                new Grouping(new Literal(45.67)));

        var rpn = new RpnPrinter();
        System.out.println(expression.accept(rpn));

        expression = new Binary(
                new Literal(2),
                new Token(TokenType.PLUS, "+", null, 1),
                new Binary(
                        new Literal(5),
                        new Token(TokenType.STAR, "*", null, 1),
                        new Binary(
                                new Literal(3),
                                new Token(TokenType.SLASH, "/", null, 1),
                                new Binary(
                                        new Literal(10),
                                        new Token(TokenType.PLUS, "+", null, 1),
                                        new Literal(3)
                                )
                        )
                )
        );
        System.out.println(expression.accept(rpn));
    }
}

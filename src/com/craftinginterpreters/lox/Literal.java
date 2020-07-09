package com.craftinginterpreters.lox;

import java.util.List;

class Literal extends Expr {

    Token operator;
    Expr right;

    Literal(Token operator, Expr right) {
        this.operator = operator;
        this.right = right;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
        return visitor.visitLiteral(this);
    }
}

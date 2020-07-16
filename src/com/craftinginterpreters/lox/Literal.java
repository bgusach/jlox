package com.craftinginterpreters.lox;

import java.util.List;

class Literal extends Expr {

    Object value;

    Literal(Object value) {
        this.value = value;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
        return visitor.visitLiteral(this);
    }
}

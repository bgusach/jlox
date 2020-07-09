package com.craftinginterpreters.lox;

import java.util.List;

class Grouping extends Expr {

    Expr expression;

    Grouping(Expr expression) {
        this.expression = expression;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
        return visitor.visitGrouping(this);
    }
}

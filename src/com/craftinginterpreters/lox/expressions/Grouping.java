package com.craftinginterpreters.lox.expressions;

public class Grouping extends Expr {

    public Expr expression;

    public Grouping(Expr expression) {
        this.expression = expression;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitGrouping(this);
    }
}

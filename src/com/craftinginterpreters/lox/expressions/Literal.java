package com.craftinginterpreters.lox.expressions;

public class Literal extends Expr {

    public Object value;

    public Literal(Object value) {
        this.value = value;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitLiteral(this);
    }
}

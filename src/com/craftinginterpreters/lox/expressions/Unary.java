package com.craftinginterpreters.lox.expressions;

import com.craftinginterpreters.lox.Token;

public class Unary extends Expr {

    public Token operator;
    public Expr right;

    public Unary(Token operator, Expr right) {
        this.operator = operator;
        this.right = right;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitUnary(this);
    }
}

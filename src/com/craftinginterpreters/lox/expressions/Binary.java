package com.craftinginterpreters.lox.expressions;

import com.craftinginterpreters.lox.Token;

public class Binary extends Expr {

    public Expr left;
    public Token operator;
    public Expr right;

    public Binary(Expr left, Token operator, Expr right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitBinary(this);
    }
}

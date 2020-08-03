package com.craftinginterpreters.lox.expressions;

public abstract class Expr {

    public abstract <R> R accept(ExprVisitor<R> visitor);

}

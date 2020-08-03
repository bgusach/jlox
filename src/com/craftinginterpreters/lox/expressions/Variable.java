package com.craftinginterpreters.lox.expressions;

import com.craftinginterpreters.lox.Token;

public class Variable extends Expr {

    public Token name;

    public Variable(Token name) {
        this.name = name;
    }

    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitVariable(this);
    }

}

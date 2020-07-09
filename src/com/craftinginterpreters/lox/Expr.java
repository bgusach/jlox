package com.craftinginterpreters.lox;

public abstract class Expr {

    abstract <R> R accept(Visitor<R> visitor);

}

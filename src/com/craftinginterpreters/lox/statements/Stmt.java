package com.craftinginterpreters.lox.statements;

public abstract class Stmt {

    public abstract void accept(StmtVisitor visitor);

}

package com.craftinginterpreters.lox.statements;

import com.craftinginterpreters.lox.Token;
import com.craftinginterpreters.lox.expressions.Expr;
import com.craftinginterpreters.lox.statements.Stmt;
import com.craftinginterpreters.lox.statements.StmtVisitor;

public class VarStmt extends Stmt {

    public Token name;
    public Expr initializer;

    public VarStmt(Token name, Expr initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    public void accept(StmtVisitor visitor) {
        visitor.visitVarStmt(this);
    }

}

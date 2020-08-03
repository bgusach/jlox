package com.craftinginterpreters.lox.statements;

import com.craftinginterpreters.lox.expressions.Expr;

public class PrintStmt extends Stmt {

    public Expr expression;

    public PrintStmt(Expr expression) {
        this.expression = expression;
    }

    @Override
    public void accept(StmtVisitor visitor) {
        visitor.visitPrintStmt(this);
    }

}

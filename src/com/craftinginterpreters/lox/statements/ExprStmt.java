package com.craftinginterpreters.lox.statements;

import com.craftinginterpreters.lox.expressions.Expr;

public class ExprStmt extends Stmt {

    public Expr expression;

    public ExprStmt(Expr expression) {
        this.expression = expression;
    }

    @Override
    public void accept(StmtVisitor visitor) {
        visitor.visitExprStmt(this);
    }

}

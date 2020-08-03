package com.craftinginterpreters.lox.statements;

public interface StmtVisitor {

    void visitExprStmt(ExprStmt exprStmt);
    void visitPrintStmt(PrintStmt printStmt);
    void visitVarStmt(VarStmt varStmt);

}

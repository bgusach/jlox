package com.craftinginterpreters.lox.expressions;

import com.craftinginterpreters.lox.expressions.*;

public interface ExprVisitor<R> {
    public R visitBinary(Binary binary);
    public R visitGrouping(Grouping grouping);
    public R visitLiteral(Literal literal);
    public R visitUnary(Unary unary);
    public R visitVariable(Variable var);
}

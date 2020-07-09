package com.craftinginterpreters.lox;

interface Visitor<R> {
    R visitBinary(Binary binary);
    R visitGrouping(Grouping grouping);
    R visitLiteral(Literal literal);
    R visitUnary(Unary unary);
}

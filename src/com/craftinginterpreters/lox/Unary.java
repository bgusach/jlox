package com.craftinginterpreters.lox;

import java.util.List;

class Unary extends Expr {

    Token operator;
    Expr right;

    Unary(Token operator, Expr right) {
        this.operator = operator;
        this.right = right;
    }
}

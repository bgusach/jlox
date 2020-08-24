Yet another Java implementation of lox

Precedence and associativity of operators::

    (sorted in descending precedence)

    Unary (right): ! -
    Multiplication (left): / *
    Addition (left): - +
    Comparison (left): > >= < <=
    Equality (left): ==  !=
    Ternary conditional (right): ?:
    Comma operator (left): ,


Production rules::

    program → declaration* EOF;
    declaration → varDecl | statement;
    varDecl → "var" IDENTIFIER ("=" expression)? ";";
    statement → exprStmt | printStmt;
    exprStmt → expression ";";
    printStmt → "print" expression ";";
    expression → equality;
    equality → comparison ( ( "!=" | "==" ) comparison )* ;
    comparison → addition ( ( ">" | ">=" | "<" | "<=" ) addition )* ;
    addition → multiplication ( ( "-" | "+" ) multiplication )* ;
    multiplication → unary ( ( "/" | "*" ) unary )* ;
    unary → ( "!" | "-" ) unary | primary ;
    primary → NUMBER | STRING | "false" | "true" | "nil"
            | "(" expression ")" | IDENTIFIER;

    # To be implemented: ternary-cond → (ternary-cond ? ternary-cond : ternary-cond) | equality


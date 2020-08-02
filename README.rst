Yet another Java implementation of lox

Precedence and associativity of operators
=========================================
(sorted in descending precedence)

Unary (right): ! -
Multiplication (left): / *
Addition (left): - +
Comparison (left): > >= < <=
Equality (left): ==  !=
Ternary conditional (right): ?:
Comma operator (left): ,


Production rules
================
expression → comma-expression;
comma-expression → ternary-cond ("," ternary-cond)* ;
ternary-cond → (ternary-cond ? ternary-cond : ternary-cond) | equality
equality → comparison ( ( "!=" | "==" ) comparison )* ;
comparison → addition ( ( ">" | ">=" | "<" | "<=" ) addition )* ;
addition → multiplication ( ( "-" | "+" ) multiplication )* ;
multiplication → unary ( ( "/" | "*" ) unary )* ;
unary → ( "!" | "-" ) unary | primary ;
primary → NUMBER | STRING | "false" | "true" | "nil" | "(" expression ")" ;

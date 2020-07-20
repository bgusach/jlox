Yet another Java implementation of lox

Precedence and associativity of operators
=========================================
Comma operator (left): ,
Unary (right): ! -
Multiplication (left): / *
Addition (left): - +
Comparison (left): > >= < <=
Equality (left): ==  !=


Production rules
================
expression → comma-expression;
comma-expression → equality ("," equality)* ;
equality → comparison ( ( "!=" | "==" ) comparison )* ;
comparison → addition ( ( ">" | ">=" | "<" | "<=" ) addition )* ;
addition → multiplication ( ( "-" | "+" ) multiplication )* ;
multiplication → unary ( ( "/" | "*" ) unary )* ;
unary → ( "!" | "-" ) unary | primary ;
primary → NUMBER | STRING | "false" | "true" | "nil" | "(" expression ")" ;

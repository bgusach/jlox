package com.craftinginterpreters.lox;

import java.util.List;
import static com.craftinginterpreters.lox.TokenType.*;

public class Parser {

    private final List<Token> tokens;
    private int current = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * Given a set of token types, checks if the current token matches
     * ony of them. If that's the case, advances the current token and
     * returns true, otherwise returns false **without** advancing.
     */
    private boolean match(TokenType... types) {
        for (var type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if current token is of the passed type. It doesn't advance counter.
     * (unless token list is exhausted, as in that case it'll always return false)
     */
    private boolean check(TokenType type) {
        if (isAtEnd()) return false;

        return peek().type == type;
    }

    /**
     * Increases the token counter by one, and returns
     * what was until right now the current token.
     */
    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == EOF;
    }

    /**
     * Returns current token without advancing counter
     */
    private Token peek() {
        return tokens.get(current);
    }

    /**
     * Returns previous token
     */
    private Token previous() {
        return tokens.get(current - 1);
    }


    private Expr expression() {
        return commaExpression();
    }

    private Expr commaExpression() {
        var expr = equality();

        while (match(COMMA)) {
            expr = new Binary(expr, previous(), equality());
        }

        return expr;
    }

    private Expr equality() {
        var expr = comparison();

        while (match(BANG_EQUAL, EQUAL_EQUAL)) {
            // Left associative, therefore nest on the left side of the binary
            expr = new Binary(expr, previous(), comparison());
        }

        return expr;
    }

    private Expr comparison() {
        var expr = addition();

        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            expr = new Binary(expr, previous(), addition());
        }

        return expr;
    }

    private Expr addition() {
        var expr = multiplication();

        while (match(PLUS, MINUS)) {
            expr = new Binary(expr, previous(), addition());
        }

        return expr;
    }

    private Expr multiplication() {
        var expr = unary();

        while (match(SLASH, STAR)) {
            expr = new Binary(expr, previous(), addition());
        }

        return expr;
    }

    private Expr unary() {
        if (match(BANG, MINUS)) {
            return new Unary(previous(), unary());
        }

        return primary();
    }

    private Token consume(TokenType type, String errorMsg) {
        if (check(type)) return advance();

        throw error(peek(), errorMsg);
    }

    private ParseError error(Token token, String errorMsg) {
        Lox.error(token, errorMsg);
        return new ParseError();
    }

    private static class ParseError extends RuntimeException {}

    /**
     * To be used on syntax errors to avoid cascading thousands of errors.
     * Discard tokens until we hit a new statement, and keep going from there.
     *
     * Better approach than panicking and have the users fix one error after the other.
     */
    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().type == SEMICOLON) return;

            switch (peek().type) {
                case CLASS:
                case FUN:
                case VAR:
                case FOR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN:
                    return;
            }

            advance();
        }
    }

    private Expr primary() {
        if (match(TRUE)) return new Literal(true);
        if (match(FALSE)) return new Literal(false);
        if (match(NIL)) return new Literal(null);

        if (match(NUMBER, STRING)) return new Literal(previous().literal);

        if (match(LEFT_PAREN)) {
            var expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression");
            return new Grouping(expr);
        }

        throw error(peek(), "Expression expected.");
    }

    Expr parse() {
        try {
            return expression();
        } catch (ParseError exc) {
            return null;
        }

    }
}

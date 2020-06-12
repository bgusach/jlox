package com.craftinginterpreters.lox;

import java.util.List;
import java.util.ArrayList;

import static com.craftinginterpreters.lox.TokenType.*;

class Scanner {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {

        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        var c = advance();

        switch (c) {
            case '(': addToken(LEFT_PAREN); return;

            case ')': addToken(RIGHT_PAREN); return;

            case '{': addToken(LEFT_BRACE); return;

            case '}': addToken(RIGHT_BRACE); return;

            case ',': addToken(COMMA); return;

            case '.': addToken(DOT); return;

            case '-': addToken(MINUS); return;

            case '+': addToken(PLUS); return;

            case ';': addToken(SEMICOLON); return;

            case '*': addToken(STAR); return;

            case '!': addToken(nextMatch('=') ? BANG_EQUAL : BANG); return;

            case '=': addToken(nextMatch('=') ? EQUAL_EQUAL : EQUAL); return;

            case '<': addToken(nextMatch('=') ? LESS_EQUAL : LESS); return;

            case '>': addToken(nextMatch('=') ? GREATER_EQUAL : GREATER); return;

            case '/':
                if (nextMatch('/')) {
                    // Drop till end of line
                    while (peek() != '\n' && !isAtEnd()) { advance(); }

                } else {
                    addToken(SLASH);
                }
                return;

            case ' ': return;

            case '\r': return;

            case '\t': return;

            case '\n': line++; return;

            case '"': string(); return;

            default:
                if (Character.isDigit(c)) {
                    number();
                    return;
                }

                Lox.error(line, String.format("Unexpected character: %s", c));
        }
    }

    private void number() {
        while (isDigit(peek())) {
            advance();
        }

        // Accept . as fractional part delimiter only if
        // followed by more numbers.
        if (peek() == '.' && isDigit(peekNext())) {
            advance();

            while (isDigit(peek())) {
                advance();
            }
        }

        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    private void string() {
        while (true) {
            if (isAtEnd()) {
                Lox.error(line, "Unterminated string.");
                return;
            }

            var c = advance();

            if (c == '"') { break; }
            if (c == '\n') { line++; }
        }

        addToken(STRING, source.substring(start, current));
    }

    private char advance() {
        current++;
        return source.charAt(current - 1);
    }

    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }

        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) {
            return '\0';
        }

        return source.charAt(current + 1);
    }

    /**
     * If next char matches the passed one, it advances and returns false.
     * If it doesn't, just returns false.
     */
    private boolean nextMatch(char ch) {
        if (isAtEnd()) { return false; }

        if (peek() == ch) {
            advance();
            return true;
        }

        return false;

    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        var text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}

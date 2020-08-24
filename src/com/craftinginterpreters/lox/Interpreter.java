package com.craftinginterpreters.lox;

import com.craftinginterpreters.lox.expressions.*;
import com.craftinginterpreters.lox.statements.*;

import java.util.List;

public class Interpreter implements ExprVisitor<Object>, StmtVisitor {

    @Override
    public Object visitLiteral(Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitGrouping(Grouping expr) {
        return evaluate(expr.expression);
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public Object visitUnary(Unary expr) {
        var right = evaluate(expr.right);

        switch (expr.operator.type){
            case MINUS:
                checkNumberOperand(expr.operator, right);
                return -(double)right;

            case BANG:
                return !isTruthy(right);
        }

        return null;
    }

    private boolean isTruthy(Object obj) {
        if (obj == null) return false;
        if (obj instanceof Boolean) return (boolean) obj;

        return true;
    }

    @Override
    public Object visitBinary(Binary expr) {
        var left = evaluate(expr.left);
        var right = evaluate(expr.right);

        switch (expr.operator.type) {
            case MINUS:
                checkNumberOperands(expr.operator, left, right);
                return (double)left - (double)right;

            case SLASH:
                checkNumberOperands(expr.operator, left, right);
                if ((double)right == 0) {
                    throw new RuntimeError(expr.operator, "Division by zero? you crazy bro??");
                }
                return (double)left / (double)right;

            case STAR:
                checkNumberOperands(expr.operator, left, right);
                return (double)left * (double)right;

            case PLUS:
                if (left instanceof Double && right instanceof Double) {
                    return (double)left + (double)right;
                }

                if (left instanceof String && right instanceof String) {
                    return left + (String)right;
                }

                throw new RuntimeError(expr.operator, "Operands must be two numbers or two strings.");

            case GREATER:
                checkNumberOperands(expr.operator, left, right);
                return (double)left > (double)right;

            case GREATER_EQUAL:
                checkNumberOperands(expr.operator, left, right);
                return (double)left >= (double)right;

            case LESS:
                checkNumberOperands(expr.operator, left, right);
                return (double)left < (double)right;

            case LESS_EQUAL:
                checkNumberOperands(expr.operator, left, right);
                return (double)left <= (double)right;

            case BANG_EQUAL:
                return !isEqual(left, right);

            case EQUAL_EQUAL:
                return isEqual(left, right);
        }

        return null;
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null) {
            return b == null;
        }

        return a.equals(b);
    }

    private void checkNumberOperand(Token operator, Object operand) {
        if (operand instanceof Double) return;

        throw new RuntimeError(operator, "Operand must be a number.");
    }

    private void checkNumberOperands(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double) return;

        throw new RuntimeError(operator, "Operands must be numbers.");
    }

    void interpret(List<Stmt> statements) {
        try {
            for (var statement : statements) {
                execute(statement);
            }
        } catch (RuntimeError error) {
            Lox.runtimeError(error);
        }
    }

    private void execute(Stmt statement) {
        statement.accept(this);
    }

    private String stringify(Object obj) {
        if (obj == null) return "nil";

        if (obj instanceof Double) {
            var text = obj.toString();

            if (text.endsWith(".0")) {
                return text.substring(0, text.length() - 2);
            }
        }

        return obj.toString();
    }

    @Override
    public void visitExprStmt(ExprStmt exprStmt) {
        evaluate(exprStmt.expression);
    }

    @Override
    public void visitPrintStmt(PrintStmt printStmt) {
        var value = evaluate(printStmt.expression);
        System.out.println(stringify(value));
    }

    @Override
    public Object visitVariable(Variable var) {
        return null;
    }

    @Override
    public void visitVarStmt(VarStmt varStmt) {

    }
}

package Calculadora;

import java.util.Stack;

public class Operaciones {

    // Operaciones lógicas (and, or, not, xor, etc.)
    public boolean not(boolean a) { return !a; }
    public boolean and(boolean a, boolean b) { return a && b; }
    public boolean or(boolean a, boolean b) { return a || b; }
    public boolean xor(boolean a, boolean b) { return a ^ b; }

    // Evaluar la ecuación booleana
    public boolean evaluate(String expr, boolean[] values) {
        Stack<Boolean> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == ' ') continue;
            if (c == '(') {
                operatorStack.push(c);
            } else if (Character.isLetter(c)) {
                operandStack.push(getValue(c, values));
            } else if (c == ')') {
                while (operatorStack.peek() != '(') {
                    operandStack.push(applyOperator(operatorStack.pop(), operandStack));
                }
                operatorStack.pop();
            } else {
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(c)) {
                    operandStack.push(applyOperator(operatorStack.pop(), operandStack));
                }
                operatorStack.push(c);
            }
        }
        while (!operatorStack.isEmpty()) {
            operandStack.push(applyOperator(operatorStack.pop(), operandStack));
        }
        return operandStack.pop();
    }

    private boolean getValue(char c, boolean[] values) {
        return values[c - 'a'];
    }

    private int precedence(char operator) {
        switch (operator) {
            case '!': return 3;
            case '&': return 2;
            case '|': return 1;
            case '^': return 1;
            default: return 0;
        }
    }

    private boolean applyOperator(char operator, Stack<Boolean> operandStack) {
        boolean result;
        if (operator == '!') {
            boolean a = operandStack.pop();
            result = not(a);
        } else {
            boolean b = operandStack.pop();
            boolean a = operandStack.pop();
            switch (operator) {
                case '&': result = and(a, b); break;
                case '|': result = or(a, b); break;
                case '^': result = xor(a, b); break;
                default: throw new IllegalArgumentException("Operador inválido: " + operator);
            }
        }
        return result;
    }
}


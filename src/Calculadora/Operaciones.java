package Calculadora;

import java.util.Stack;

/**
 * Clase para realizar operaciones booleanas y evaluar expresiones booleanas.
 */
public class Operaciones {

    public boolean not(boolean a) {
        return !a;
    }

    public boolean and(boolean a, boolean b) {
        return a && b;
    }

    public boolean or(boolean a, boolean b) {
        return a || b;
    }

    public boolean xor(boolean a, boolean b) {
        return a ^ b;
    }

    /**
     * Evalúa una expresión booleana.
     * @param expr La expresión booleana.
     * @param values Los valores de las variables.
     * @return El resultado de la evaluación.
     */
    public boolean evaluate(String expr, boolean[] values) {
        Stack<Boolean> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == ' ') continue;
            if (c == '(') {
                operatorStack.push(c);
            } else if (Character.isLetter(c)) {
                boolean value = getValue(c, values);
                operandStack.push(value);
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
        c = Character.toUpperCase(c); // Convertir a mayúscula
        int index = c - 'A'; // Calcular índice

        // Validar que el índice esté dentro del rango de valores
        if (index < 0 || index >= values.length) {
            throw new IllegalArgumentException("Variable fuera de rango: " + c);
        }

        return values[index];
    }

    private int precedence(char operator) {
        return switch (operator) {
            case '!' -> 3;
            case '&' -> 2;
            case '^' -> 1;
            case '|' -> 0;
            default -> -1;
        };
    }

    private boolean applyOperator(char operator, Stack<Boolean> operandStack) {
        boolean result;
        if (operator == '!') {
            // NOT solo requiere un operando
            if (operandStack.isEmpty()) {
                throw new IllegalArgumentException("Operandos insuficientes para el operador: " + operator);
            }
            boolean a = operandStack.pop();
            result = not(a);
        } else {
            // AND, OR, XOR requieren dos operandos
            if (operandStack.size() < 2) {
                throw new IllegalArgumentException("Operandos insuficientes para el operador: " + operator);
            }
            boolean b = operandStack.pop();
            boolean a = operandStack.pop();
            result = switch (operator) {
                case '&' -> and(a, b);
                case '|' -> or(a, b);
                case '^' -> xor(a, b);
                default -> throw new IllegalArgumentException("Operador inválido: " + operator);
            };
        }
        return result;
    }
}
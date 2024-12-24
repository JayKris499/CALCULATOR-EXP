package com.example.calculator.Service;

import java.util.Stack;

import org.springframework.stereotype.Service;
import com.example.calculator.DTO.ExpressionDto;

@Service
public class CalculatorImpl implements Calculator {
	
	public double Calculate(ExpressionDto express) {
	    String expression = express.getExpression(); // Ensure this gets the correct string
	    Stack<Double> operands = new Stack<>();
	    Stack<Character> operators = new Stack<>();
	    
	    for (int i = 0; i < expression.length(); i++) {
	        char c = expression.charAt(i);
	        if (Character.isWhitespace(c)) {
	            continue;
	        }
	        if (Character.isDigit(c)) {
	            StringBuilder sb = new StringBuilder();
	            while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
	                sb.append(expression.charAt(i++));
	            }
	            i--;
	            operands.push(Double.parseDouble(sb.toString()));
	        } else if (c == '(') {
	            operators.push(c);
	        } else if (c == ')') {
	            while (operators.peek() != '(') {
	                operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()));
	            }
	            operators.pop();
	        } else if (isOperator(c)) {
	            while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
	                operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()));
	            }
	            operators.push(c);
	        }
	    }
	    while (!operators.isEmpty()) {
	        operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()));
	    }
	    return operands.pop();
	}

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

    private double applyOperation(char operation, double b, double a) {
        switch (operation) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
        }
        return 0;
    }
}

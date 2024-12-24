package com.example.calculator.Controller;

import com.example.calculator.DTO.ExpressionDto;
import com.example.calculator.Service.CalculatorImpl;

import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class CalculatorController {

	@Autowired
	CalculatorImpl calculator;

	@GetMapping("/")
	public String showCalculator() {
		return "calculator";
	}

	@PostMapping("/calculate")
	public String calculate(@RequestParam("expression") String expression, Model model) {
		if (!isValidExpression(expression)) {
			model.addAttribute("error", "Invalid characters in expression.");
			return "calculator";
		}
		if (!areBracketsBalanced(expression)) {
			model.addAttribute("error", "Unmatched brackets in expression.");
			return "calculator";
		}
		if (hasEmptyBrackets(expression)) {
			model.addAttribute("error", "Empty brackets in expression.");
			return "calculator";
		}
		if (hasConsecutiveOperators(expression)) {
			model.addAttribute("error", "Consecutive operators in expression.");
			return "calculator";
		}

		ExpressionDto dto = new ExpressionDto(expression);
		try {
			double result = calculator.Calculate(dto);
			model.addAttribute("result", result);
		} catch (UnsupportedOperationException e) {
			model.addAttribute("error", e.getMessage());
		}
		return "calculator";
	}

	private boolean isValidExpression(String expression) {
	    return expression.matches("[0-9+\\-*/(). ]+");
	}

	private boolean areBracketsBalanced(String expression) {
	    Stack<Character> stack = new Stack<>();
	    for (char c : expression.toCharArray()) {
	        if (c == '(') {
	            stack.push(c);
	        } else if (c == ')') {
	            if (stack.isEmpty() || stack.pop() != '(') {
	                return false;
	            }
	        }
	    }
	    return stack.isEmpty();
	}

	private boolean hasEmptyBrackets(String expression) {
	    return expression.contains("()");
	}

	private boolean hasConsecutiveOperators(String expression) {
	    return expression.matches(".*[+\\-*/]{2,}.*");
	}
}

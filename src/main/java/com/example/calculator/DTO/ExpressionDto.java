package com.example.calculator.DTO;

public class ExpressionDto {
	@Override
	public String toString() {
		return "ExpressionDto [expression=" + expression + "]";
	}

	String expression;

	public ExpressionDto(String expression) {
		this.expression = expression;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
}


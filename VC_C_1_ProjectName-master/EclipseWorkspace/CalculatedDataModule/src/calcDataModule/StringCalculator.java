package calcDataModule;

import java.util.Scanner;
import java.util.Stack;

public final class StringCalculator {
	
	public static double calculate(String str){
		Scanner scan = new Scanner(str);
		double returnValue = 0;
		Stack<String> outputQueue = new Stack<String>();
		Stack<String> operatorQueue = new Stack<String>();
		
		// Shunting-yard Algorithm
		while(scan.hasNext()){
			String next = scan.next();
			if(isNumeric(next))
				outputQueue.push(next);
			else if(isOperator(next)){
				if(!operatorQueue.isEmpty())
				while(!operatorQueue.isEmpty() && operatorCompare(operatorQueue.peek(), next) <= 0){
					outputQueue.push(operatorQueue.pop());
				}
				operatorQueue.push(next);
			}
			else if(next.equals("(")){
				operatorQueue.push(next);
			}
			else if(next.equals(")")){
				while(!operatorQueue.peek().equals("(")){
					outputQueue.push(operatorQueue.pop());
				}
				operatorQueue.pop();
			}
		}
		
		while(!operatorQueue.isEmpty()){
			outputQueue.push(operatorQueue.pop());
		}
		
		String outputString = "";
		while(!outputQueue.isEmpty()){
			outputString += outputQueue.pop() + " ";
		}
		scan.close();
		
		outputString = reverseNotation(outputString);
		
		returnValue = calculateReversePolish(outputString);
		return returnValue;
	}
	
	private static double calculateReversePolish(String str){
		System.out.println(str);
		Stack<String> calculation = new Stack<String>();
		Scanner scan = new Scanner(str);
		while(scan.hasNext()){
			String next = scan.next();
			if(isOperator(next)){
				double operand2 = Double.parseDouble(calculation.pop());
				double operand1 = Double.parseDouble(calculation.pop());
				double result = 0;
				switch(next){
				case "+": result = operand1 + operand2;
					break;
				case "-": result = operand1 - operand2;
					break; 
				case "*": result = operand1 * operand2;
					break;
				case "/": result = operand1 / operand2;
					break;
				}
				calculation.push(Double.toString(result));
			} else if(isNumeric(next)){
				calculation.push(next);
			}
		}
		scan.close();
		return Double.parseDouble(calculation.pop());
	}
	
	private static boolean isNumeric(String str){
		return (str != null) && str.matches("[-+]?\\d*\\.?\\d+");
	}
	
	private static boolean isOperator(String str){
		return (str != null) && str.matches("[+-/*]");
	}
	
	private static int operatorCompare(String op1, String op2){
		int op1Precedence = 0;
		switch(op1){
			case "+": op1Precedence = 2;
				break;
			case "-": op1Precedence = 2;
				break; 
			case "*": op1Precedence = 3;
				break;
			case "/": op1Precedence = 3;
				break;
		}
		
		int op2Precedence = 0;
		switch(op2){
			case "+": op2Precedence = 2;
				break;
			case "-": op2Precedence = 2;
				break; 
			case "*": op2Precedence = 3;
				break;
			case "/": op2Precedence = 3;
				break;
		}
		
		return op2Precedence - op1Precedence;
	}
	
	private static String reverseNotation(String str){
		Stack<String> reverser = new Stack<String>();
		Scanner scan = new Scanner(str);
		while(scan.hasNext()){
			reverser.push(scan.next());
		}
		
		String output = "";
		while(!reverser.isEmpty()){
			output += reverser.pop() + " ";
		}
		scan.close();
		return output;
	}
}

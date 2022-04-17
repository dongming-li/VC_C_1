package DataProcessing;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Static utility class responsible for calculating expressions based on an input string.
 * The class is strictly for utility and not be be instantiated.
 */
public final class ExpressionCalculator {

    /**
     * Calculates a numerical value based on a string input.
     * Utilizes the Shunting-Yard algorithm to transfer expression into Reverse Polish Notation.
     * @param str A purely numeric string expression
     * @return String value of calculated expressions
     */
    public static String calculate(String str){
        Scanner scan = new Scanner(str);
        String returnValue = "";
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

    /**
     * Calculates a string expression in Reverse Polish Notation
     * @param str A string in Reverse Polish Notation
     * @return String format of calculated result
     */
    private static String calculateReversePolish(String str){
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
        return calculation.pop();
    }

    /**
     * Returns true if the string is numeric
     * @param str
     * @return Returns true if string is numeric
     */
    private static boolean isNumeric(String str){
        return (str != null) && str.matches("[-+]?\\d*\\.?\\d+");
    }

    private static boolean isOperator(String str){
        return (str != null) && str.matches("[+-/*]");
    }

    /**
     * Determines precedence of operators to manage order of operations
     * @param op1 First operator
     * @param op2 Second operator
     * @return The difference of op2 and op1
     */
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

    /**
     * Reverses a string expression in Polish Notation to Reverse Polish Notation
     * @param str String expression in Polish Notation
     * @return String expression in Reverse Polish Notation
     */
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

    /**
     * Returns an expression with variable dependencies into a purely numeric form.
     * Recursively follows through expressions that can branch through numerous variable dependencies
     * @param expression Expression to be processed
     * @param expressionNames List of all expression names for a given activity
     * @param expressions List of all expressions for a given activity
     * @return A purely numeric expression
     */
    public static String processExpression(String expression, ArrayList<String> expressionNames, ArrayList<String> expressions){
        String toReturn = "";
        Scanner scan = new Scanner(expression);
        while(scan.hasNext()){
            String next = scan.next();
            if(next.charAt(0) == '$'){
                next = findExpressionMatch(next.substring(1), expressionNames, expressions);
            }
            toReturn += next + " ";
        }
        return toReturn;
    }

      /**
      * Finds the expression from a given expression name.
      * The found expression will then be sent to processExpression() for variable
      * @param name expression name to find
      * @param expressionNames List of all expression names for a given activity
      * @param expressions List of all expressions for a given activity
      * @return The processed expression in a purely numeric form
      */
    private static String findExpressionMatch(String name, ArrayList<String> expressionNames, ArrayList<String> expressions){
        String toReturn = "";
        int counter = 0;
        while(true){
            String dataPointName = expressionNames.get(counter);
            if(dataPointName.equals(name)){
                String tempExpression = expressions.get(counter);
                toReturn = processExpression(tempExpression, expressionNames, expressions);
                return toReturn;
            }
            counter++;
        }
    }
}
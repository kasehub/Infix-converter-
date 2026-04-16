import java.util.*;

public class InfixConverter {

    // Helper: check if character is an operator
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
    }

    // Helper: get precedence of operator
    private static int precedence(char op) {
        switch (op) {
            case '^': return 3;
            case '*':
            case '/': return 2;
            case '+':
            case '-': return 1;
            default: return -1;
        }
    }

    // Convert infix expression to postfix
    public static String infixToPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char ch : infix.toCharArray()) {
            // Operand (letter or digit)
            if (Character.isLetterOrDigit(ch)) {
                output.append(ch);
            }
            // Left parenthesis
            else if (ch == '(') {
                stack.push(ch);
            }
            // Right parenthesis
            else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop());
                }
                stack.pop(); // remove '('
            }
            // Operator
            else if (isOperator(ch)) {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(ch)) {
                    output.append(stack.pop());
                }
                stack.push(ch);
            }
        }

        // Pop remaining operators
        while (!stack.isEmpty()) {
            output.append(stack.pop());
        }
        return output.toString();
    }

    // Convert infix expression to prefix
    public static String infixToPrefix(String infix) {
        // Step 1: Reverse the infix string
        StringBuilder rev = new StringBuilder(infix).reverse();

        // Step 2: Swap '(' and ')'
        for (int i = 0; i < rev.length(); i++) {
            char c = rev.charAt(i);
            if (c == '(') {
                rev.setCharAt(i, ')');
            } else if (c == ')') {
                rev.setCharAt(i, '(');
            }
        }

        // Step 3: Convert modified reversed string to postfix
        String postfixRev = infixToPostfix(rev.toString());

        // Step 4: Reverse the result to get prefix
        return new StringBuilder(postfixRev).reverse().toString();
    }

    // Main method for demonstration
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an infix expression (single-letter operands, operators + - * / ^, parentheses): ");
        String infix = scanner.nextLine().trim();

        String postfix = infixToPostfix(infix);
        String prefix = infixToPrefix(infix);

        System.out.println("\nInfix  : " + infix);
        System.out.println("Postfix: " + postfix);
        System.out.println("Prefix : " + prefix);

        scanner.close();
    }
}
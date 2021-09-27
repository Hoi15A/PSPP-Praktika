import java.util.Stack;

/**
 * User: Karl Rege
 */

public class Calculator {

	private static final Stack<Double> stack = new Stack();

	public static void expr() throws Exception {
		term();
		while (Scanner.la == Token.PLUS 
				|| Scanner.la == Token.MINUS) {
			Scanner.scan();
			int op = Scanner.token.kind;

			term();

			switch (op) {
				case Token.PLUS -> stack.push(stack.pop() + stack.pop());
				case Token.MINUS -> stack.push(- stack.pop() + stack.pop());
			}
		}
	}

	public static void term() throws Exception {
		factor();
		while (Scanner.la == Token.TIMES || Scanner.la == Token.SLASH) {
			Scanner.scan();
			int op = Scanner.token.kind;

			factor();

			switch (op) {
				case Token.TIMES -> stack.push(stack.pop() * stack.pop());
				case Token.SLASH -> {
					double a = stack.pop();
					double b = stack.pop();
					stack.push(b / a);
				}
			}
		}
	}

	public static void factor() throws Exception {
		if (Scanner.la == Token.LBRACK) {
			Scanner.scan();
			expr();
			Scanner.check(Token.RBRACK);
		} else if (Scanner.la == Token.NUMBER) {
			Scanner.scan();
			stack.push(Scanner.token.val);
		}
	}
	
	public static double start(String expr) throws Exception {
       	Scanner.init(expr);
       	Scanner.scan();
       	expr();
       	// return result
       	return stack.pop();
	}    


   public static void main(String[] args) throws Exception {
   	    System.out.println("result="+start("3+2-4"));
   }
      
}

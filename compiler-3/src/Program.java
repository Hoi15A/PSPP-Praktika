import de.inetsoftware.jwebassembly.JWebAssembly;
import de.inetsoftware.jwebassembly.module.Emitter;
import de.inetsoftware.jwebassembly.module.NumericOperator;
import de.inetsoftware.jwebassembly.module.ValueType;
import de.inetsoftware.jwebassembly.module.ValueTypeConvertion;
import de.inetsoftware.jwebassembly.module.WasmBlockInstruction;
import de.inetsoftware.jwebassembly.module.WasmBlockOperator;
import de.inetsoftware.jwebassembly.module.WasmConstInstruction;
import de.inetsoftware.jwebassembly.module.WasmConvertInstruction;
import de.inetsoftware.jwebassembly.module.WasmLoadStoreInstruction;
import de.inetsoftware.jwebassembly.module.WasmNumericInstruction;

/**
 * User: Karl Rege
 */

interface ICalculator2 {
	double calc(double arg0);
}


public class Program implements ICalculator2, Emitter {

	public static void expr() throws Exception {
		term();
		while (Scanner.la == Token.PLUS
				|| Scanner.la == Token.MINUS) {
			Scanner.scan();
			int op = Scanner.token.kind;

			term();

			switch (op) {
				case Token.PLUS: JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.add, ValueType.f64, 0)); break;
				case Token.MINUS: JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.sub, ValueType.f64, 0)); break;
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
				case Token.TIMES: JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.mul, ValueType.f64, 0)); break;
				case Token.SLASH: JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.div, ValueType.f64, 0)); break;
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
			JWebAssembly.il.add(new WasmConstInstruction(Scanner.token.val, 0));

		} else if (Scanner.la == Token.IDENT) {
			Scanner.scan();
			if ("E".equals(Scanner.token.str)) {
				JWebAssembly.il.add(new WasmConstInstruction(Math.E, 0));
			} else if ("PI".equals(Scanner.token.str)) {
				JWebAssembly.il.add(new WasmConstInstruction(Math.PI, 0));
			} else {
				JWebAssembly.il.add(new WasmLoadStoreInstruction(true,
						JWebAssembly.local(ValueType.f64, Scanner.token.str), 0));
			}
		}
	}

	public static void assignment() throws Exception {
		if (Scanner.la == Token.IDENT) {
			Scanner.scan();
			String varName = Scanner.token.str;
			Scanner.check(Token.EQUAL);
			expr();
			JWebAssembly.il.add(new WasmLoadStoreInstruction(false,
					JWebAssembly.local(ValueType.f64, varName), 0));
			Scanner.check(Token.SCOLON);
		}
	}

	public static void returnStatement() throws Exception {
		Scanner.check(Token.RETURN);
		expr();
		Scanner.check(Token.SCOLON);
		JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.RETURN, null, 0));
	}

	public static void ifStatement() throws Exception {
		Scanner.check(Token.IF);
		condition();
		JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.IF, null, 0));
		statement();

		if (Scanner.la == Token.ELSE) {
			JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.ELSE, null, 0));
			Scanner.scan();
			statement();
		}

		JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.END, null, 0));
	}

	public static void condition() throws Exception {
		Scanner.check(Token.LBRACK);
		boolean exprNegative = false;
		if (Scanner.la == Token.NOT) {
			Scanner.scan();
			exprNegative = true;
		}

		expr();

		JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.nearest, ValueType.f64, 0));
		JWebAssembly.il.add(new WasmConvertInstruction(ValueTypeConvertion.d2i, 0));
		if(exprNegative) {
			JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.eqz, ValueType.i32, 0));
		}

		Scanner.check(Token.RBRACK);
	}

	public static void block() throws Exception {
		Scanner.check(Token.LCBRACK);
		statementSequence();
		Scanner.check(Token.RCBRACK);
	}

	public static void whileStatement() throws Exception {
		Scanner.scan();
		JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.BLOCK, null, 0));
		JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.LOOP, null, 0));
		condition();
		JWebAssembly.il.add(new WasmNumericInstruction(NumericOperator.eqz, ValueType.i32, 0));
		JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.BR_IF, 1, 0));
		statement();
		JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.BR, 0, 0 ));
		JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.END, null, 0));
		JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.END, null, 0));
	}

	public static void statement() throws Exception {

		switch (Scanner.la) {
			case Token.RETURN:
				returnStatement();
				break;
			case Token.IF:
				ifStatement();
				break;
			case Token.LCBRACK:
				block();
				break;
			case Token.WHILE:
				whileStatement();
				break;
			default:
				assignment();
				break;
		}
	}

	public static void statementSequence() throws Exception {
		while (Scanner.la == Token.IDENT ||
				Scanner.la == Token.RETURN ||
				Scanner.la == Token.IF ||
				Scanner.la == Token.LCBRACK ||
				Scanner.la == Token.WHILE) {
			statement();
		}
	}

	public static void program() throws Exception {
		statementSequence();
		JWebAssembly.il.add(new WasmLoadStoreInstruction(true, JWebAssembly.local(ValueType.f64, "value"), 0));
		JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.UNREACHABLE, 0));
	}


	public static void main(String[] args) throws Exception {
		Scanner.init("4.2 + 3.2*2");
		Scanner.scan();
		JWebAssembly.emitCode(ICalculator2.class, new Program());
	}

	@Override
	public double calc(double arg0) {
		return 0;
	}

	@Override
	public void emit() {
		try {
			program();
			JWebAssembly.il.add(new WasmBlockInstruction(WasmBlockOperator.RETURN, null, 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

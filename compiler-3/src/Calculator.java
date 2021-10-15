import de.inetsoftware.jwebassembly.JWebAssembly;
import de.inetsoftware.jwebassembly.module.Emitter;
import de.inetsoftware.jwebassembly.module.NumericOperator;
import de.inetsoftware.jwebassembly.module.ValueType;
import de.inetsoftware.jwebassembly.module.WasmConstInstruction;
import de.inetsoftware.jwebassembly.module.WasmNumericInstruction;

public class Calculator implements ICalculator, Emitter {

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
			}
		}
	}


	public static void main(String[] args) throws Exception {
		Scanner.init("4.2 + 3.2*2");
		Scanner.scan();
		JWebAssembly.emitCode(ICalculator.class, new Calculator());
	}

	@Override
	public double calc() {
		return 0;
	}

	@Override
	public void emit() {
		try {
			expr();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

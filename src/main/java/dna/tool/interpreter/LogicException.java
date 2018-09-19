package dna.tool.interpreter;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import dna.antlr.ValidationException;

public class LogicException extends ValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LogicException(ParserRuleContext ctx, Exception e) {
		super(ctx, e);
	}

	public LogicException(ParserRuleContext ctx, String fmt, Object... args) {
		super(ctx, fmt, args);
	}

	public LogicException(Token symbol, String fmt, Object... args) {
		super(symbol, fmt, args);
	}

}

package dna.antlr;

import static java.lang.String.format;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class ValidationException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	public static final String message = "line %d:%d: %s";

	public ValidationException(ParserRuleContext ctx, String fmt, Object... args) {
		super(format(message, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine(), format(fmt, args)));
	}

	public ValidationException(Token symbol, String fmt, Object... args) {
		super(format(message, symbol.getLine(), symbol.getCharPositionInLine(), format(fmt, args)));
	}

	public ValidationException(ParserRuleContext ctx, Exception e) {
		super(format(message, ctx.getStart().getLine(), ctx.getStart().getCharPositionInLine()), e);
	}

}

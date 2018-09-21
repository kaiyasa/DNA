package dna.tool.interpreter;

import org.antlr.v4.runtime.tree.TerminalNode;

import dna.antlr.DnaParser;
import dna.antlr.DnaParser.ExpressionParenContext;
import dna.antlr.DnaParser.IdentifierContext;
import dna.antlr.DnaParser.LiteralContext;
import dna.antlr.ValidationException;

public class ExpressionVisitor extends DnaCommonVisitor<Storage<?>> {

	private DnaModel model;

	public ExpressionVisitor(DnaModel model) {
		this.model = model;
	}

	@Override
	public Storage<?> visitLiteral(LiteralContext ctx) {
		if (ctx.getToken(DnaParser.INT, 0) != null)
			return new InternalStorage<Integer>(asInteger(ctx.INT()));
		else if (ctx.getToken(DnaParser.STRING, 0) != null)
			return new InternalStorage<String>(unquote(ctx.STRING()));

		throw new LogicException(ctx, "unknown literal category");
	}

	@Override
	public Storage<?> visitIdentifier(IdentifierContext ctx) {
		String name = ctx.getText();

		Variable identifier = model.variable(name)
				.orElseThrow(() -> new ValidationException(ctx, "undefined variable '%s'", name));
		return identifier.data;
	}

	@Override
	public Storage<?> visitExpressionParen(ExpressionParenContext ctx) {
		return visit(ctx.expression());
	}

	private Integer asInteger(TerminalNode terminal) {
		try {
			return Integer.parseInt(terminal.getText());
		} catch (NumberFormatException e) {
			throw new ValidationException(terminal.getSymbol(), e);
		}
	}

	private String unquote(TerminalNode terminal) {
		String text = terminal.getText().trim();
		int i = 0, j = text.length() - 1;

		if (text.length() > 1 && text.charAt(i) == '"' && text.charAt(j) == '"')
			return ((j - 1) - (i + 1) + 1 == 0) ? "" : text.substring(i + 1, j);

		throw new ValidationException(terminal.getSymbol(), "invalid quoted string - '%s'", text);
	}
}

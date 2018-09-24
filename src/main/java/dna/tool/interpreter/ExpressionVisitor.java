package dna.tool.interpreter;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import dna.antlr.DnaParser;
import dna.antlr.DnaParser.CallSiteContext;
import dna.antlr.DnaParser.ExpressionContext;
import dna.antlr.DnaParser.IdentifierContext;
import dna.antlr.DnaParser.LiteralContext;
import dna.antlr.ValidationException;

public class ExpressionVisitor extends DnaCommonVisitor<Storage<?>> {

	private DnaModel model;

	public ExpressionVisitor(DnaModel model) {
		this.model = model;
	}

	private Integer asInteger(TerminalNode terminal) {
		try {
			return Integer.parseInt(terminal.getText());
		} catch (NumberFormatException e) {
			throw new ValidationException(terminal.getSymbol(), e);
		}
	}

	private Storage<?> perform(Storage<?> r1, Token op, Storage<?> r2) {
		if (r1.get().getClass() == String.class)
			if (op.getType() == DnaParser.PLUS)
				return store(r1.typeOf(String.class).get() + r2.typeOf(String.class).get());

			else
				throw new ValidationException(op, "unsupported operation on string types");
		else {
			int v1 = r1.typeOf(Integer.class).get();
			int v2 = r2.typeOf(Integer.class).get();
			int r;

			switch (op.getType()) {
			default:
				throw new ValidationException(op, "unsupported operation on int types");
			case DnaParser.PLUS:
				r = v1 + v2;
				break;
			case DnaParser.DASH:
				r = v1 - v2;
				break;
			case DnaParser.ASTERISK:
				r = v1 * v2;
				break;
			case DnaParser.SLASH:
				r = v1 / v2;
				break;
			}
			return store(r);
		}
	}

	private <R> Storage<R> store(R value) {
		return new InternalStorage<R>(value);
	}

	private String unquote(TerminalNode terminal) {
		String text = terminal.getText().trim();
		int i = 0, j = text.length() - 1;

		if (text.length() > 1 && text.charAt(i) == '"' && text.charAt(j) == '"')
			return ((j - 1) - (i + 1) + 1 == 0) ? "" : text.substring(i + 1, j);

		throw new ValidationException(terminal.getSymbol(), "invalid quoted string - '%s'", text);
	}

	@Override
	public Storage<?> visitExpression(ExpressionContext ctx) {
		if (ctx.expr1 != null)
			if (ctx.op == null)
				return visit(ctx.expr1);
			else {
				Storage<?> r1 = visit(ctx.expr1);
				Storage<?> r2 = visit(ctx.expr2);

				if (r1.get().getClass() != r2.get().getClass())
					throw new ValidationException(ctx.expr2, "type mismatch in expression");
				return perform(r1, ctx.op, r2);
			}
		else {
			return visitAlternatives(ctx.callSite(), ctx.atom());
		}
	}

	@Override
	public Storage<?> visitIdentifier(IdentifierContext ctx) {
		String name = ctx.getText();

		Variable identifier = model.variable(name)
				.orElseThrow(() -> new ValidationException(ctx, "undefined variable '%s'", name));
		return identifier.data;
	}

	@Override
	public Storage<?> visitLiteral(LiteralContext ctx) {
		if (ctx.getToken(DnaParser.INT, 0) != null)
			return store(asInteger(ctx.INT()));

		else if (ctx.getToken(DnaParser.STRING, 0) != null)
			return store(unquote(ctx.STRING()));

		throw new LogicException(ctx, "unknown literal category");
	}

	@Override
	public Storage<?> visitCallSite(CallSiteContext ctx) {
		Map<String, Function<List<Storage<?>>, Storage<?>>> routines = new HashMap<>();
		String name = ctx.identifier().getText();
		List<Storage<?>> args = ctx.expression().stream().map((x) -> visit(x)).collect(toList());

		if (routines.containsKey(name))
			return routines.get(name).apply(args);
		throw new ValidationException(ctx.identifier(), "undefined routine name '%s'", name);
	}

}

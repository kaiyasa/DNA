package dna.tool.interpreter;

import org.antlr.v4.runtime.tree.TerminalNode;

import dna.antlr.DnaParser;
import dna.antlr.DnaParser.LiteralContext;

public class ExpressionVisitor extends DnaCommonVisitor<Storage<?>> {
	@Override
	public Storage<?> visitLiteral(LiteralContext ctx) {
		if (ctx.getToken(DnaParser.INT, 0) != null) {
			return new InternalStorage<Integer>(Integer.parseInt(ctx.INT().getText()));

		} else if (ctx.getToken(DnaParser.STRING, 0) != null) {
			return new InternalStorage<String>(unquote(ctx.STRING()));
		} else {
			throw new LogicException(ctx, "unkown literal category");
		}
	}

	private String unquote(TerminalNode string) {
		// TODO Auto-generated method stub
		return null;
	}
}

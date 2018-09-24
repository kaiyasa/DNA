package dna.tool.interpreter;

import java.util.function.Function;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;

import dna.antlr.DnaBaseVisitor;

public class DnaCommonVisitor<T> extends DnaBaseVisitor<T> {

	@Override
	public T visit(ParseTree tree) {
		// pass the null back and prevent needless NPEs
		return tree == null ? null : super.visit(tree);
	}

	public <C extends RuleNode, R> R visit(C ctx, Function<C, R> lambda) {
		return new DnaLambdaVisitor<C, R>(lambda).visit(ctx);
	}

	public <C extends RuleNode> T visitAlternatives(@SuppressWarnings("unchecked") C... contexts) {
		for (C ctx : contexts)
			if (ctx != null)
				return visit(ctx);
		return null;
	}

}

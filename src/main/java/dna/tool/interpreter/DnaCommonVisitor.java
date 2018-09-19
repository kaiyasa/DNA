package dna.tool.interpreter;

import java.util.function.Function;

import org.antlr.v4.runtime.tree.RuleNode;

import dna.antlr.DnaBaseVisitor;

public class DnaCommonVisitor<T> extends DnaBaseVisitor<T> {

	public <C extends RuleNode, R> R visit(C ctx, Function<C, R> lambda) {
		return new DnaLambdaVisitor<C, R>(lambda).visit(ctx);
	}

}

package dna.tool.interpreter;

import java.util.function.Function;

import org.antlr.v4.runtime.tree.RuleNode;

public class DnaLambdaVisitor<C extends RuleNode, R> extends DnaCommonVisitor<R> {

	protected Function<C, R> lambda;

	protected boolean firstCall = false;

	public DnaLambdaVisitor(Function<C, R> lambda) {
		super();
		this.lambda = lambda;
	}

	@SuppressWarnings("unchecked")
	@Override
	public R visitChildren(RuleNode node) {
		if (firstCall)
			return super.visitChildren(node);
		else {
			firstCall = true;
			try {
				return lambda.apply((C) node);
			} finally {
				firstCall = false;
			}
		}
	}

}

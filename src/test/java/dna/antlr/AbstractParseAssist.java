package dna.antlr;

import java.util.function.Function;
import java.util.function.Supplier;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * simple foundation class for building, configuring and visiting ANTLR 4
 * grammars
 *
 * @param <P>
 *            parser class
 * @param <C>
 *            rule context class
 * @param <R>
 *            return type used in the Visitor class
 * @param <V>
 *            Visitor class
 */
public abstract class AbstractParseAssist<P extends Parser, C extends ParseTree, R, V extends ParseTreeVisitor<R>>
		implements ParseAssist<P, R> {
	public AbstractParseAssist(ParserFactory<P> parserFactory, Supplier<V> visitorFactory, Function<P, C> ruleParser) {
		this.parserFactory = parserFactory;
		this.visitorFactory = visitorFactory;
		this.ruleParser = ruleParser;
	}

	public R actOn(String text) {
		try {
			return actRet = (visitor = visitorFactory.get()).visit(ruleParser.apply(parser = parseOn(text)));
		} catch (IllegalStateException e) {
			ve = e;
			throw e;
		}
	}

	protected Supplier<V> visitorFactory;
	protected Function<P, C> ruleParser;
	protected ParserFactory<P> parserFactory;

	protected R actRet = null;
	protected IllegalStateException ve = null;

	/** latest used visitor instance */
	protected V visitor;

	/** latest used function object for calling specified rule method on parser */
	protected P parser;
}
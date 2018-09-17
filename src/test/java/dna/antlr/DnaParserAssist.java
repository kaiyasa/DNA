package dna.antlr;

import java.util.function.Function;
import java.util.function.Supplier;

import org.antlr.v4.runtime.tree.ParseTree;
import static dna.antlr.ParserFactory.from;

/** DNA based parsing assist foundation */
public class DnaParserAssist<C extends ParseTree, R, V extends DnaVisitor<R>>
		extends AbstractParseAssist<DnaParser, C, R, V> {
	public DnaParserAssist(Supplier<V> visitorFactory, Function<DnaParser, C> ruleParser) {
		super(new ParserFactory<>(DnaLexer::new, DnaParser::new), visitorFactory, ruleParser);

	}

	@Override
	public DnaParser parseOn(String text) {
		return parserFactory.create(from(text), new ExceptionUsingErrorListener());
	}

}
package dna.antlr;

import java.util.function.Function;
import java.util.function.Supplier;

import org.antlr.v4.runtime.tree.ParseTree;
import static dna.antlr.ParserFactory.from;

/** DNA based parsing assist foundation */
public class DnaParserAssist<R, C extends ParseTree, V extends DnaVisitor<R>>
		extends ANTLR4ParseAssist<R, DnaParser, C, V> {
	public DnaParserAssist(Supplier<V> visitorFactory, Function<DnaParser, C> ruleParser) {
		super(new ParserFactory<>(DnaLexer::new, DnaParser::new), visitorFactory, ruleParser);

	}

	@Override
	public DnaParser parseOn(String text) {
		return parserFactory.create(from(text), new ExceptionUsingErrorListener());
	}

}

package dna.antlr;

import java.util.function.Function;
import java.util.function.Supplier;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * base test fixture for DNA grammar
 * 
 * @author dan.miner
 *
 * @param <R>
 *            result type of visiting the parse tree
 * @param <C>
 *            rule context of the specific parse rule
 * @param <V>
 *            visitor type
 */
public class DnaParseTestBase<R, C extends ParseTree, V extends DnaVisitor<R>> extends DnaParserAssist<R, C, V> {

	public DnaParseTestBase(Supplier<V> visitorFactory, Function<DnaParser, C> ruleParser) {
		super(visitorFactory, ruleParser);
	}

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

}

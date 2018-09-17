package dna.antlr;

import java.util.function.Function;
import java.util.function.Supplier;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

public class ParsingTestBase<C extends ParseTree, R, V extends DnaVisitor<R>> extends DnaParserAssist<C, R, V> {

	public ParsingTestBase(Supplier<V> visitorFactory, Function<DnaParser, C> ruleParser) {
		super(visitorFactory, ruleParser);
	}

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

}

package dna.tool.interpreter;

import org.junit.Test;

import dna.antlr.DnaParseTestBase;
import dna.antlr.DnaParser;
import dna.antlr.DnaParser.XUnitContext;
import dna.antlr.ValidationException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class DnaInterpreterTest extends DnaParseTestBase<Void, XUnitContext, DnaInterpreter> {
	public DnaInterpreterTest() {
		super(DnaInterpreter::new, DnaParser::xUnit);
	}

	@Test
	public void oneVariableAsInt() {
		actOn("int a");

		item = visitor.variable("a");
		assertThat(item.typeInfo.kind(), equalTo(TypeInfo.Kind.INT));
		assertThat(item.name, equalTo("a"));
	}
	
	@Test(expected=ValidationException.class)
	public void oneVariableAsStruct() {
		actOn("Data a");

		item = visitor.variable("a");
		assertThat(item.typeInfo.kind(), equalTo(TypeInfo.Kind.INT));
		assertThat(item.name, equalTo("a"));
	}
	
	@Test
	public void oneVariableAsString() {
		actOn("string a");

		item = visitor.variable("a");
		assertThat(item.typeInfo.kind(), equalTo(TypeInfo.Kind.STRING));
		assertThat(item.name, equalTo("a"));
	}

	private Variable item ;
}

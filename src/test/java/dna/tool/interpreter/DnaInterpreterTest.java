package dna.tool.interpreter;

import org.junit.Test;

import dna.antlr.DnaParseTestBase;
import dna.antlr.DnaParser;
import dna.antlr.DnaParser.XUnitContext;
import dna.antlr.ValidationException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

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

	@Test(expected = ValidationException.class)
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

	@Test
	public void twoVariables() {
		actOn("int b, c");

		item = visitor.variable("b");
		assertThat(item.name, equalTo("b"));
		assertThat(item.typeInfo.kind(), equalTo(TypeInfo.Kind.INT));

		item = visitor.variable("c");
		assertThat(item.name, equalTo("c"));
		assertThat(item.typeInfo.kind(), equalTo(TypeInfo.Kind.INT));
	}

	@Test
	public void redefineVariable() {
		try {
			actOn("int b \n string b");
			fail("exception expected");
		} catch (ValidationException e) {
			assertThat(e.getMessage(), containsString("redefinition of variable"));
		}
	}

	private Variable item;
}

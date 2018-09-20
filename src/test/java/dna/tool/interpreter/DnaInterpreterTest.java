package dna.tool.interpreter;

import org.junit.Test;

import dna.antlr.DnaParseTestBase;
import dna.antlr.DnaParser;
import dna.antlr.DnaParser.XUnitContext;
import dna.antlr.ValidationException;
import dna.tool.interpreter.TypeInfo.Kind;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

public class DnaInterpreterTest extends DnaParseTestBase<Void, XUnitContext, DnaInterpreter> {

	private Variable item;

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

	@Test
	public void assignInteger() {
		actOn("int B\nB = 42");

		item = checkVariable("B", Kind.INT);
		assertThat(dereferenceAsInteger(item.data), equalTo(42));
	}

	@Test
	public void assignString() {
		actOn("string B\nB = \"Don't panic\"");

		item = checkVariable("B", Kind.STRING);
		assertThat(dereferenceAsString(item.data), equalTo("Don't panic"));
	}

	@Test
	public void undefinedVariable() {
		try {
			actOn("B = 666");
			fail("exception expected");
		} catch (ValidationException e) {
			assertThat(e.getMessage(), containsString("undefined variable"));
		}
	}

	@Test
	public void typeMismatchLiteral() {
		try {
			actOn("string B \n B = 666");
			fail("exception expected");
		} catch (ValidationException e) {
			assertThat(e.getMessage(), containsString("type mismatch"));
		}
	}

	private Integer dereferenceAsInteger(Storage<?> value) {
		return value.typeOf(Integer.class).get();
	}

	private String dereferenceAsString(Storage<?> value) {
		return value.typeOf(String.class).get();
	}

	private Variable checkVariable(String name, Kind type) {
		Variable identifier = visitor.variable(name);

		assertThat(identifier, not(nullValue()));
		assertThat(identifier.name, equalTo(name));
		assertThat(identifier.typeInfo.kind(), equalTo(type));
		return identifier;
	}
}

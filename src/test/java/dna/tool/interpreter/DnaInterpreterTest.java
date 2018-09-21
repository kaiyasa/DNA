package dna.tool.interpreter;

import org.junit.Ignore;
import org.junit.Test;

import dna.antlr.DnaParseTestBase;
import dna.antlr.DnaParser;
import dna.antlr.DnaParser.XUnitContext;
import dna.tool.interpreter.TypeInfo.Kind;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Optional;

public class DnaInterpreterTest extends DnaParseTestBase<Void, XUnitContext, DnaInterpreter> {

	private Variable item;

	public DnaInterpreterTest() {
		super(DnaInterpreter::new, DnaParser::xUnit);
	}

	@Test
	public void oneVariableAsInt() {
		actOn("int D");

		assertVariable("D", Kind.INT);
	}

	@Ignore
	@Test
	public void oneVariableAsStruct() {
		actOn("Data S");

		assertVariable("S", Kind.INT);
	}

	@Test
	public void oneVariableAsString() {
		actOn("string F");

		assertVariable("F", Kind.STRING);
	}

	@Test
	public void twoVariables() {
		actOn("int b, c");

		assertVariable("b", Kind.INT);
		assertVariable("c", Kind.INT);
	}

	@Test
	public void redefineVariable() {
		errorOn("int b \n string b");
		assertThat(exception(), containsString("redefinition of variable"));
	}

	@Test
	public void assignInteger() {
		actOn("int B\nB = 42");

		item = assertVariable("B", Kind.INT);
		assertThat(dereferenceAsInteger(item.data), equalTo(42));
	}

	@Test
	public void assignString() {
		actOn("string B\nB = \"Don't panic\"");

		item = assertVariable("B", Kind.STRING);
		assertThat(dereferenceAsString(item.data), equalTo("Don't panic"));
	}

	@Test
	public void undefinedVariable() {
		errorOn("B = 666");

		assertThat(exception(), containsString("undefined variable"));
	}

	@Test
	public void typeMismatchLiteral() {
		errorOn("string B \n B = 666");

		assertThat(exception(), containsString("type mismatch"));
	}

	@Test
	public void assignFromVariable() {
		actOn("int B, C \n B = 12 \n C = B");

		item = assertVariable("B", Kind.INT);
		assertThat(dereferenceAsInteger(item.data), equalTo(12));

		item = assertVariable("C", Kind.INT);
		assertThat(dereferenceAsInteger(item.data), equalTo(12));
	}

	@Test
	public void typeMismatchIdentifier() {
		errorOn("int B \n string C \n B = 12 \n C = B");

		assertThat(exception(), containsString("type mismatch"));
	}

	@Test
	public void expressionParentheses() {
		actOn("string C \n C = (\"panic\")");

		item = assertVariable("C", Kind.STRING);
		assertThat(dereferenceAsString(item.data), equalTo("panic"));
	}

	// helper or common code and tests

	private Integer dereferenceAsInteger(Storage<?> value) {
		return value.typeOf(Integer.class).get();
	}

	private String dereferenceAsString(Storage<?> value) {
		return value.typeOf(String.class).get();
	}

	private Variable assertVariable(String name, Kind type) {
		Optional<Variable> identifier = visitor.variable(name);
		assertThat(identifier.isPresent(), equalTo(true));

		Variable variable = identifier.get();
		assertThat(variable.name, equalTo(name));
		assertThat(variable.typeInfo.kind(), equalTo(type));

		return variable;
	}
}

package dna.tool.interpreter;

import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import dna.antlr.DnaParseTestBase;
import dna.antlr.DnaParser;
import dna.antlr.DnaParser.ExpressionContext;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class ExpressionVisitorTest extends DnaParseTestBase<Storage<?>, ExpressionContext, ExpressionVisitor> {

	private String strValue;
	private Object intValue;

	public ExpressionVisitorTest() {
		super(() -> new ExpressionVisitor(new TestModel()), DnaParser::expression);
	}

	static class TestModel implements DnaModel {

		private Map<String, Variable> symbols;

		@Override
		public Optional<Variable> variable(String name) {
			return Optional.ofNullable(symbols.get(name));
		}

		TestModel add(String name, String value) {
			symbols.put(name, new Variable(name, new StringType(), -1, -1));
			return this;
		}

		TestModel add(String name, Integer value) {
			symbols.put(name, new Variable(name, new IntType(), -1, -1));
			return this;
		}

	}

	@Test
	public void stringLiteral() {
		exprAsString("\"panic\"");

		assertThat(strValue, equalTo("panic"));
	}

	@Test
	public void intLiteral() {
		exprAsInt("12");

		assertThat(intValue, equalTo(12));
	}

	@Test
	public void expressionParen() {
		exprAsString("(\"panic\")");

		assertThat(strValue, equalTo("panic"));
	}

	@Test
	public void multiply() {
		exprAsInt("2*3");

		assertThat(intValue, equalTo(6));
	}

	private void exprAsString(String text) {
		actOn(text);
		strValue = actRet.typeOf(String.class).get();
	}

	private void exprAsInt(String text) {
		actOn(text);
		intValue = actRet.typeOf(Integer.class).get();
	}

}

package dna.tool.interpreter;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;

import dna.antlr.DnaLexer;
import dna.antlr.DnaParser;
import dna.antlr.ExceptionUsingErrorListener;
import dna.antlr.ParserFactory;
import dna.antlr.ValidationException;
import dna.antlr.DnaParser.SimpleDefinitionContext;
import dna.antlr.DnaParser.TypeContext;

public class DnaInterpreter extends DnaCommonVisitor<Void> {
	private ParserFactory<DnaParser> parserFactory = new ParserFactory<>(DnaLexer::new, DnaParser::new);
	private DnaParser parser;

	private Map<String, Variable> symbols = new HashMap<>();

	public Void run(CharStream source) {
		parser = parserFactory.create(source, new ExceptionUsingErrorListener());

		return visit(parser.xUnit());
	}

	public Variable variable(String name) {
		return symbols.get(name);
	}

	private Variable variable(Token token, String name, TypeInfo ti) {
		return new Variable(name, ti, token.getLine(), token.getCharPositionInLine());
	}

	@Override
	public Void visitSimpleDefinition(SimpleDefinitionContext ctx) {
		TypeInfo typeInfo = visit(ctx.type(), (type) -> {
			if (type.identifier() != null)
				throw new LogicException(type, "struct references unsupported");
			else if (type.getToken(DnaParser.K_int, 0) != null)
				return new IntType();
			else if (type.getToken(DnaParser.K_string, 0) != null)
				return new StringType();
			else
				throw new LogicException(type, "unknown type specifier category");
		});

		ctx.identifier().stream().forEach((definition) -> {
			String name = definition.ID().getText();

			if (symbols.containsKey(name)) {
				Variable old = variable(name);
				throw new ValidationException(definition, "redefinition of variable '%s' from line %d:%d", name,
						old.line, old.charAt);
			}
			symbols.put(name, variable(definition.getStart(), name, typeInfo));
		});

		return null;
	}
}

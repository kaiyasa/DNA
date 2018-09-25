package dna.tool.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;

import dna.antlr.DnaLexer;
import dna.antlr.DnaParser;
import dna.antlr.ExceptionUsingErrorListener;
import dna.antlr.ParserFactory;
import dna.antlr.ValidationException;
import dna.antlr.DnaParser.AssignmentContext;
import dna.antlr.DnaParser.SimpleDefinitionContext;

public class DnaInterpreter extends DnaCommonVisitor<Void> implements DnaModel {
	private ParserFactory<DnaParser> parserFactory = new ParserFactory<>(DnaLexer::new, DnaParser::new);
	private DnaParser parser;

	private Map<String, Variable> symbols = new HashMap<>();

	private Map<String, Function<List<Storage<?>>, Storage<?>>> routines = new HashMap<>();

	@Override
	public Optional<Function<List<Storage<?>>, Storage<?>>> routine(String name) {
		return Optional.ofNullable(routines.get(name));
	}

	public Void run(CharStream source) {
		parser = parserFactory.create(source, new ExceptionUsingErrorListener());

		return visit(parser.xUnit());
	}

	@Override
	public Optional<Variable> variable(String name) {
		return Optional.ofNullable(symbols.get(name));
	}

	private Variable variable(Token token, String name, TypeInfo ti) {
		return new Variable(name, ti, token.getLine(), token.getCharPositionInLine());
	}

	@Override
	public Void visitAssignment(AssignmentContext ctx) {
		String name = ctx.identifier().getText();
		Variable identifier = variable(name)
				.orElseThrow(() -> new ValidationException(ctx.identifier(), "undefined variable '%s'", name));

		Storage<?> data = Optional.ofNullable(new ExpressionVisitor(this).visit(ctx.expression()))
				.orElseThrow(() -> new LogicException(ctx.expression(), "result of expression processing is null"));

		if (identifier.typeInfo.match(data.get().getClass())) {
			identifier.data = data;
			return null;
		}

		throw new ValidationException(ctx.identifier(), "type mismatch on expression");

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

		ctx.identifier().stream().forEach((identifier) -> {
			String name = identifier.getText();

			variable(name).ifPresent((previous) -> {
				throw new ValidationException(identifier, "redefinition of variable '%s' from line %d:%d", name,
						previous.line, previous.charAt);
			});

			symbols.put(name, variable(identifier.getStart(), name, typeInfo));
		});

		return null;
	}

}

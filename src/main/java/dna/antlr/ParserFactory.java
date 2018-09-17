package dna.antlr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Function;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Recognizer;

public class ParserFactory<P extends Parser> {
	private <R extends Recognizer<?, ?>> R setUp(R recognizer, ANTLRErrorListener listener) {
		if (listener != null) {
			recognizer.removeErrorListeners();
			recognizer.addErrorListener(listener);
		}

		return recognizer;
	}

	public ParserFactory(Function<CharStream, Lexer> lexerFactory, Function<CommonTokenStream, P> parserFactory) {
		super();
		this.lexerFactory = lexerFactory;
		this.parserFactory = parserFactory;
	}

	public P create(CharStream input, ANTLRErrorListener listener) {
		Lexer lexer = setUp(lexerFactory.apply(input), listener);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		P parser = setUp(parserFactory.apply(tokens), listener);

		return parser;
	}

	public static CharStream from(File file) throws IOException {
		return CharStreams.fromFileName(file.getCanonicalPath());
	}

	public static CharStream from(Path path) throws IOException {
		return CharStreams.fromPath(path);
	}

	public static CharStream from(String text) {
		return CharStreams.fromString(text);
	}

	private Function<CharStream, Lexer> lexerFactory;
	private Function<CommonTokenStream, P> parserFactory;

}

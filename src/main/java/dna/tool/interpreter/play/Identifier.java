package dna.tool.interpreter.play;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.antlr.v4.runtime.Token;

class Identifier implements Iterable<Token> {
	private List<Token> path;

	public Identifier(List<Token> components) {
		path = new ArrayList<>(components);
	}

	public int size() {
		return path.size();
	}

	public Token head() {
		return path.get(0);
	}

	public Identifier tail() {
		return new Identifier(path.subList(1, path.size()));
	}

	@Override
	public Iterator<Token> iterator() {
		return path.iterator();
	}

}

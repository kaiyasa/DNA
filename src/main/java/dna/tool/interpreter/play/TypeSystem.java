package dna.tool.interpreter.play;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.antlr.v4.runtime.Token;

import dna.antlr.ValidationException;
import dna.tool.interpreter.LogicException;

class TypeSystem {

	Field field(Types.Kind kind, Token declaration) {
		switch (kind) {
		default:
			throw new LogicException(declaration, "unknown type requested");
		case INT:
			return new Field(new IntType(), declaration);
		case STRING:
			return new Field(new StrType(), declaration);
		case STRUCT:
			return new Field(resolveType(declaration.getText()).orElseThrow(() -> {
				return new ValidationException(declaration, "undefined type '%s'", declaration.getText());
			}), declaration);
		}
	}

	public Optional<Types<?>> resolveType(String text) {
		return Optional.ofNullable(reg.get(text));
	}

	public StructuredType addType(Token id) {
		return add(new StructuredType(id));

	}

	public StructuredType addType(StructuredType parent, Token id) {
		return add(new StructuredType(parent, id));
	}

	private StructuredType add(StructuredType item) {
		String name = item.symbol().getText();

		resolveType(name).ifPresent((previous) -> {
			throw new ValidationException(item.symbol(), "redefinition of type '%s'", name);
		});
		reg.put(name, item);
		return item;

	}

	private Map<String, StructuredType> reg = new HashMap<>();

}
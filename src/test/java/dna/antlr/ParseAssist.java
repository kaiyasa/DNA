package dna.antlr;

public interface ParseAssist<P, R> {
	/** create parser configured to parse given text */
	P parseOn(String text);

	/** perform the actual parse and visit actions */
	R actOn(String text);

}
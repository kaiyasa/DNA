package dna.antlr;

/**
 * interface to capture the ability to create a parser and call a parsing rule
 * 
 * @author dan.miner
 *
 * @param <R>
 *            result type returned by parsing
 * @param <P>
 *            type argument for the parser
 */
public interface ParseAssist<R, P> {
	/** create parser configured to parse given text */
	P parseOn(String text);

	/** perform the actual parse and visit actions */
	R actOn(String text);

}

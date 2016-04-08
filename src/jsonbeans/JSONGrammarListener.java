package jsonbeans;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JSONGrammarParser}.
 */
public interface JSONGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JSONGrammarParser#json}.
	 * @param ctx the parse tree
	 */
	void enterJson(JSONGrammarParser.JsonContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONGrammarParser#json}.
	 * @param ctx the parse tree
	 */
	void exitJson(JSONGrammarParser.JsonContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONGrammarParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(JSONGrammarParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONGrammarParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(JSONGrammarParser.ObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONGrammarParser#pair}.
	 * @param ctx the parse tree
	 */
	void enterPair(JSONGrammarParser.PairContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONGrammarParser#pair}.
	 * @param ctx the parse tree
	 */
	void exitPair(JSONGrammarParser.PairContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONGrammarParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(JSONGrammarParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONGrammarParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(JSONGrammarParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONGrammarParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(JSONGrammarParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONGrammarParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(JSONGrammarParser.ValueContext ctx);
}
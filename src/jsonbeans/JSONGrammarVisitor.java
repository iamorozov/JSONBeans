package jsonbeans;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JSONGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JSONGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JSONGrammarParser#json}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJson(JSONGrammarParser.JsonContext ctx);
	/**
	 * Visit a parse tree produced by {@link JSONGrammarParser#object}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject(JSONGrammarParser.ObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link JSONGrammarParser#pair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPair(JSONGrammarParser.PairContext ctx);
	/**
	 * Visit a parse tree produced by {@link JSONGrammarParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(JSONGrammarParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link JSONGrammarParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(JSONGrammarParser.ValueContext ctx);
}
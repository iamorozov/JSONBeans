package jsonbeans;

import jsonbeans.recognizer.JSONLexer;
import jsonbeans.recognizer.JSONParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Created by Morozov Ivan on 19.03.2016.
 *
 * Class to read Beans from JSON
 */
public class JSONDecoder{

    String jsonString;

    JSONDecoder(String jsonString){
        this.jsonString = jsonString;
    }

    public Object readJSON(){

        ANTLRInputStream inputStream = new ANTLRInputStream(jsonString);
        JSONLexer lexer = new JSONLexer(inputStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokens);
        ParseTree tree = parser.json();

        JSONBeanVisitor visitor = new JSONBeanVisitor();

        return visitor.visit(tree);
    }
}

package jsonbeans;


import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;

/**
 * Created by Morozov Ivan on 07.04.2016.
 *
 * Class used for lexical division of json object
 */

public class JSONTokenizer {

    // Token types
    public static final int TYPE_INT_CONST   = 1;
    public static final int TYPE_SYMBOL      = 2;
    public static final int TYPE_IDENTIFIER  = 3;

    public static final char LEFT_BRACE = '{';
    public static final char RIGHT_BRACE = '}';
    public static final char LEFT_BRACKET = '[';
    public static final char RIGHT_BRACKET = ']';
    public static final char COLON = ':';
    public static final char COMMA = ',';
    public static final char QUOTE   = '\"';
    public static final String KW_CLASS = "class";

    StreamTokenizer tokenizer;

    private Hashtable reservedSymbols;

    private String currentToken;

    private int tokenType;

    JSONTokenizer(String jsonString){

        StringReader stringReader = new StringReader(jsonString);
        try{
            initializeTokenizer(stringReader);
        }
        catch (IOException e){
            //TODO: manage exceptions
        }
    }

    private void initializeTokenizer(Reader input)
            throws IOException{
        tokenizer = new StreamTokenizer(input);

        tokenizer.parseNumbers();

        tokenizer.whitespaceChars(COMMA, COMMA);
        tokenizer.whitespaceChars(QUOTE, QUOTE);

        tokenizer.ordinaryChar(LEFT_BRACE);
        tokenizer.ordinaryChar(RIGHT_BRACE);
        tokenizer.ordinaryChar(LEFT_BRACKET);
        tokenizer.ordinaryChar(RIGHT_BRACKET);
        tokenizer.ordinaryChar(COLON);

        initSymbols();

        if(hasMoreTokens())
            tokenizer.nextToken();
    }

    void initSymbols(){

        reservedSymbols = new Hashtable();

        reservedSymbols.put(LEFT_BRACE, LEFT_BRACE);
        reservedSymbols.put(LEFT_BRACKET, LEFT_BRACKET);
        reservedSymbols.put(RIGHT_BRACE, RIGHT_BRACE);
        reservedSymbols.put(RIGHT_BRACKET, RIGHT_BRACKET);
        reservedSymbols.put(COLON, COLON);
        reservedSymbols.put(COMMA, COMMA);
        reservedSymbols.put(QUOTE, QUOTE);
    }

    void getNextToken() throws JSONDeserializationException{
        if(!hasMoreTokens())
            throw new JSONDeserializationException();//TODO:manage exceptions

        try{
            switch (tokenizer.ttype){
                case StreamTokenizer.TT_NUMBER:
                    tokenType = TYPE_INT_CONST;
                    currentToken = String.valueOf(tokenizer.nval);
                    break;
                case StreamTokenizer.TT_WORD:
                    tokenType = TYPE_IDENTIFIER;
                    currentToken = tokenizer.sval;
                    break;
                default:
                    char charToken = (char)tokenizer.ttype;
                    if(reservedSymbols.contains(charToken)){
                        tokenType = TYPE_SYMBOL;
                        currentToken = String.valueOf(charToken);
                        break;
                    }
                    else {
                        //TODO: manage exceptions
                    }
            }

            tokenizer.nextToken();
        }
        catch (IOException e){
            //TODO: manage exceptions
        }
    }

    boolean hasMoreTokens(){return tokenizer.ttype != StreamTokenizer.TT_EOF;}


    Object readObject()throws JSONDeserializationException{

        getNextToken();

        if(!(tokenType == TYPE_SYMBOL && currentToken.equals(String.valueOf(LEFT_BRACE))))
            JSONError("Missing \'{\'", tokenizer.lineno());

        getNextToken();

        if(!(tokenType == TYPE_IDENTIFIER && currentToken.equals(KW_CLASS)))
            JSONError("Missing \"class\" keyword", tokenizer.lineno());

        getNextToken();

        if(!(tokenType == TYPE_SYMBOL && currentToken.equals(String.valueOf(COLON))))
            JSONError("Missing \':\'", tokenizer.lineno());

        getNextToken();

        if(!(tokenType == TYPE_IDENTIFIER))
            JSONError("Missing type identifier", tokenizer.lineno());

        String className = currentToken;

        try{
            Class<?> aClass = Class.forName(className);

            Object instance = aClass.newInstance();

            BeanInfo beanInfo = java.beans.Introspector.getBeanInfo(aClass);



            return instance;
        }
        catch (ClassNotFoundException e){
            JSONError("Class have not found", tokenizer.lineno());
            return null;
        }
        catch (ReflectiveOperationException e){
            JSONError("Unable to instantiate class " + className, tokenizer.lineno());
            return null;
        }
        catch (IntrospectionException e){
            JSONError("Unable to introspect class " + className, tokenizer.lineno());
            return null;
        }
    }

    public void JSONError(String message, int line)throws JSONDeserializationException{
        throw new JSONDeserializationException();//TODO: manage exceptions
    }

}

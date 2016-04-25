package jsonbeans;


import beans.Dolphin;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

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

    private Map reservedSymbols;

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

        reservedSymbols = new HashMap();

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
            JSONError("Unexpected end of file", tokenizer.lineno());

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
                    if(reservedSymbols.containsValue(charToken)){
                        tokenType = TYPE_SYMBOL;
                        currentToken = String.valueOf(charToken);
                        break;
                    }
                    else {
                        JSONError("Unexpected token: " + charToken, tokenizer.lineno());
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

            Map<String, PropertyDescriptor> propertyMap = getPropertyMap(beanInfo.getPropertyDescriptors());

            getNextToken();

            while (!(tokenType == TYPE_SYMBOL && currentToken.equals(String.valueOf(RIGHT_BRACE)))){

                if(!(tokenType == TYPE_IDENTIFIER))
                    JSONError("Missing type identifier", tokenizer.lineno());

                PropertyDescriptor property = propertyMap.get(currentToken);

                getNextToken();

                if(!(tokenType == TYPE_SYMBOL && currentToken.equals(String.valueOf(COLON))))
                    JSONError("Missing \':\'", tokenizer.lineno());

                if(JSONUtil.primitiveSet.contains(property.getPropertyType()))
                    readPrimitive(property, instance);
                else if(property instanceof IndexedPropertyDescriptor)
                    readArray();
                else
                    property.getWriteMethod().invoke(instance, readObject());


                getNextToken();
            }

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

    private void readArray() {
    }

    void readPrimitive(PropertyDescriptor property, Object instance)
            throws ReflectiveOperationException, JSONDeserializationException{

        getNextToken();

        Class<?> propertyType = property.getPropertyType();

        if(JSONUtil.numberTypes.contains(propertyType)){

            if(tokenType != TYPE_INT_CONST)
                JSONError("Wrong value!", tokenizer.lineno());

            Number value = 0;

            if(propertyType == Byte.class || propertyType == byte.class)
                value = Double.valueOf(currentToken).byteValue();

            else if(propertyType == Short.class || propertyType == short.class)
                value = Double.valueOf(currentToken).shortValue();

            else if(propertyType == Integer.class || propertyType == int.class)
                value = Double.valueOf(currentToken).intValue();

            else if(propertyType == Long.class || propertyType == long.class)
                value = Double.valueOf(currentToken).longValue();

            else if(propertyType == Short.class || propertyType == short.class)
                value = Double.valueOf(currentToken).shortValue();

            else if(propertyType == Float.class || propertyType == float.class)
                value = Double.valueOf(currentToken).floatValue();

            else if(propertyType == Double.class || propertyType == double.class)
                value = Double.valueOf(currentToken).floatValue();

            property.getWriteMethod().invoke(instance, value);
        }
        else if(JSONUtil.characterSequenceTypes.contains(propertyType)){
            if(propertyType == Character.class || propertyType == char.class)
                property.getWriteMethod().invoke(instance, currentToken.charAt(0));
            else
                property.getWriteMethod().invoke(instance, currentToken);
        }
        else if(JSONUtil.logicalTypes.contains(propertyType))
            property.getWriteMethod().invoke(instance, Boolean.valueOf(currentToken));
        else if(propertyType == Class.class)
            property.getWriteMethod().invoke(instance, Class.forName(currentToken));
    }

    Map<String, PropertyDescriptor> getPropertyMap(PropertyDescriptor[] descriptors){

        HashMap<String, PropertyDescriptor> propertyDescriptorHashMap = new HashMap<>();

        for(PropertyDescriptor descriptor: descriptors){
            propertyDescriptorHashMap.put(descriptor.getName(), descriptor);
        }

        return propertyDescriptorHashMap;
    }

    public void JSONError(String message, int line)throws JSONDeserializationException{
        throw new JSONDeserializationException(message, line);
    }

}

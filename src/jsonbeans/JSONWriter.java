package jsonbeans;

import java.io.StringWriter;

/**
 * Created by Morozov Ivan on 20.03.2016.
 *
 * Class for writing JSON to string
 */
class JSONWriter {

    private static final char LEFT_BRACE = '{';
    private static final char RIGHT_BRACE = '}';
    private static final char LEFT_BRACKET = '[';
    private static final char RIGHT_BRACKET = ']';

    private int nestingLevel = 0;

    private StringWriter writer = new StringWriter();

    private JSONWriter(){}

    public static JSONWriter getJSONWriter(){
        return new JSONWriter();
    }

    String getStringValue(Object value){
        if(value == null)
            return "null";
        else if(value instanceof Class)
            return "\"" + value.toString().substring(6) + "\"";
        else
            return value instanceof String ? "\"" + value.toString() + "\"" : value.toString();
    }

    public void write(String toAppend){
        for (int i = 0; i < nestingLevel; i++) {
            writer.append("    ");
        }

        writer.append(toAppend);
        writer.append("\n");
    }

    public void write(Character toAppend){
        write(toAppend.toString());
    }


    public void writeName(String name){
        write("\"" + name + "\"" + ":");
    }


    public void writeValue(Object value){
        write(getStringValue(value));
    }


    public void writePair(String name, Object value, boolean comma){
        if (comma)
            write("\"" + name + "\"" + ":" + getStringValue(value) + ",");
        else
            write("\"" + name + "\"" + ":" + getStringValue(value));
    }


    public void writeComma(){
        write(",");
    }


    public void writeOpenBrace(){
        write(LEFT_BRACE);
        nestingLevel++;
    }


    public void writeCloseBrace(){
        nestingLevel--;
        write(RIGHT_BRACE);
    }


    public void writeOpenBracket(){
        write(LEFT_BRACKET);
        nestingLevel++;
    }


    public void writeCloseBracket(){
        nestingLevel--;
        write(RIGHT_BRACKET);
    }


    @Override
    public String toString() {
        return writer.toString();
    }
}

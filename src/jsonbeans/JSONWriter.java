package jsonbeans;

import java.io.StringWriter;

/**
 * Created by Morozov Ivan on 20.03.2016.
 */
class JSONWriter {

    private static final char LEFT_BRACE = '{';
    private static final char RIGHT_BRACE = '}';
    private static final char LEFT_BRACKET = '[';
    private static final char RIGHT_BRACKET = ']';

    private int nestingLevel = 0;

    private StringWriter writer = new StringWriter();

    private JSONWriter(){}

    private String replaceWithCapital(String toReplace){
        StringBuilder sb = new StringBuilder(toReplace);

        String replaceWith = ((Character)toReplace.charAt(0)).toString().toUpperCase();

        sb.replace(0, 1, replaceWith);

        return toReplace = sb.toString();
    }

    public static JSONWriter getJSONWriter(){
        return new JSONWriter();
    }

    public void write(String toAppend){
        for (int i = 0; i < nestingLevel; i++) {
            writer.append("    ");
        }

        writer.append(toAppend + "\n");
    }

//    public void write(String toAppend){
//        writer.append(toAppend);
//    }

    public void write(Character toAppend){
        write(toAppend.toString());
    }



    public void writeName(String name){
        write("\"" + name + "\"" + ":");
    }

    public void writeValue(Object value){

        String stringValue = "";

        stringValue = value instanceof String ? "\"" + value.toString() + "\"" : value.toString();

        write(stringValue);
    }

    public void writePair(String name, Object value, boolean comma){

        String stringValue = "";

        stringValue = value instanceof String ? "\"" + value.toString() + "\"" : value.toString();

        if (comma)
            write("\"" + name + "\"" + ":" + stringValue + ",");
        else
            write("\"" + name + "\"" + ":" + stringValue);
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

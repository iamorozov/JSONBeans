package jsonbeans;

import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

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

    JSONWriter(){}

    public static JSONWriter getJSONWriter(){
        return new JSONWriter();
    }

    String getStringValue(Object value){
        if(value == null)
            return "null";
        else if(value instanceof Class)
            return "\"" + value.toString().substring(6) + "\"";
        else
            return (value instanceof String || value instanceof Character) ? "\"" + value.toString() + "\"" : value.toString();
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

    public void writeClass(Class beanClass, boolean comma){
        writePair("class", beanClass.getCanonicalName(), comma);
    }


    public void writeComma(){
        write(",");
    }

    void writeArrayOfPrimitives(int[] arr){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length - 1) builder.append(", ");
        }
        write(builder.toString());
    }

    void writeArrayOfPrimitives(byte[] arr){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length - 1) builder.append(", ");
        }
        write(builder.toString());
    }

    void writeArrayOfPrimitives(short[] arr){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length - 1) builder.append(", ");
        }
        write(builder.toString());
    }

    void writeArrayOfPrimitives(long[] arr){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length - 1) builder.append(", ");
        }
        write(builder.toString());
    }

    void writeArrayOfPrimitives(double[] arr){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length - 1) builder.append(", ");
        }
        write(builder.toString());
    }

    void writeArrayOfPrimitives(float[] arr){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length - 1) builder.append(", ");
        }
        write(builder.toString());
    }

    void writeArrayOfPrimitives(boolean[] arr){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append(arr[i]);
            if (i != arr.length - 1) builder.append(", ");
        }
        write(builder.toString());
    }

    void writeArrayOfPrimitives(char[] arr){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            builder.append("\"");
            builder.append(arr[i]);
            builder.append("\"");
            if (i != arr.length - 1) builder.append(", ");
        }
        write(builder.toString());
    }

    void writeArrayOfWrappers(Object[] arr){
        write(getCollectionJoiningWithCommas(arr));
    }

    /**
     * @param arr - an array, whose elements should be joined
     * @return - String representation of array's elements, joined with commas
     */
    String getCollectionJoiningWithCommas(Object[] arr) {
        return Stream.of(arr)
                .map(this::getStringValue)
                .collect(joining(", "));
    }

    void writeDefaultValue(PropertyDescriptor property, boolean comma) {
        Class<?> type = property.getPropertyType();

        if (JSONUtil.numberTypes.contains(type)) {
            writePair(property.getName(), 0, comma);
        } else if (JSONUtil.characterSequenceTypes.contains(type)) {
            writePair(property.getName(), "", comma);
        } else writePair(property.getName(), null, comma);
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

package jsonbeans;


import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Morozov Ivan on 07.03.2016.
 *
 * Class to make JSON from JavaBean class
 */
class JSONEncoder {

    //TODO: Problem with array casting and serializing of non primitive types
    //TODO: Serialize references
    //TODO: Manage exceptions
    //TODO: Refactoring
    //TODO: don't Serialize null values
    private JSONWriter jsonWriter;

    private Set<Object> serialized = new HashSet<>();

    private void saveObjectAsJSON(Object src) {
        try{
            if(!serialized.contains(src)){

                serialized.add(src);//put object into hashSet to prevent multiple serialization

                BeanInfo beanInfo = Introspector.getBeanInfo(src.getClass());

                LinkedList<PropertyDescriptor> properties = new LinkedList<>(Arrays.asList(beanInfo.getPropertyDescriptors()));
                properties.removeIf(p -> p.getName().equals("class"));

                jsonWriter.writeOpenBrace();

                boolean comma = false;//to distinguish whether comma is needed after a pair or not

                if(!properties.isEmpty())
                    comma = true;

                jsonWriter.writeClass(src.getClass(), comma);

                String propertyName;

                for (PropertyDescriptor property: properties) {

                    if (property == properties.getLast())
                        comma = false;

                    propertyName = property.getName();

                    if (!JSONUtil.primitiveSet.contains(property.getPropertyType())){

                        if(property instanceof IndexedPropertyDescriptor){
                            saveArrayAsJSON((IndexedPropertyDescriptor) property, src);
                            if(comma) jsonWriter.writeComma();
                        }
                        else {
                            Object obj = property.getReadMethod().invoke(src);
                            if (!serialized.contains(obj) && obj != null){
                                jsonWriter.writeName(propertyName);
                                saveObjectAsJSON(obj);
                                if(comma)
                                    jsonWriter.writeComma();
                            }
                        }
                    }
                    else
                        jsonWriter.writePair(propertyName, property.getReadMethod().invoke(src), comma);
                }

                jsonWriter.writeCloseBrace();
                serialized.remove(src);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void saveArrayAsJSON(IndexedPropertyDescriptor indexedProp, Object invoker) throws InvocationTargetException, IllegalAccessException {

        jsonWriter.writeName(indexedProp.getName());
        jsonWriter.writeOpenBracket();
        Class<?> propertyType = indexedProp.getPropertyType();

        if (JSONUtil.primitiveArraysSet.contains(propertyType)) {
            if (propertyType == int[].class)
                jsonWriter.writeArrayOfPrimitives((int[])indexedProp.getReadMethod().invoke(invoker));
            else if (propertyType == short[].class)
                jsonWriter.writeArrayOfPrimitives((short[])indexedProp.getReadMethod().invoke(invoker));
            else if (propertyType == byte[].class)
                jsonWriter.writeArrayOfPrimitives((byte[])indexedProp.getReadMethod().invoke(invoker));
            else if (propertyType == double[].class)
                jsonWriter.writeArrayOfPrimitives((double[])indexedProp.getReadMethod().invoke(invoker));
            else if (propertyType == float[].class)
                jsonWriter.writeArrayOfPrimitives((float[])indexedProp.getReadMethod().invoke(invoker));
            else if (propertyType == long[].class)
                jsonWriter.writeArrayOfPrimitives((long[])indexedProp.getReadMethod().invoke(invoker));
            else if (propertyType == char[].class)
                jsonWriter.writeArrayOfPrimitives((char[])indexedProp.getReadMethod().invoke(invoker));
            else if (propertyType == boolean[].class)
                jsonWriter.writeArrayOfPrimitives((boolean[])indexedProp.getReadMethod().invoke(invoker));
            else
                jsonWriter.writeArrayOfWrappers((Object[])indexedProp.getReadMethod().invoke(invoker));
        }
        else{
            try{
                Object[] objectArray = (Object[]) indexedProp.getReadMethod().invoke(invoker);

                for (int i = 0; i < objectArray.length; i++) {
                    saveObjectAsJSON(objectArray[i]);

                    if(i != objectArray.length - 1) jsonWriter.write(',');
                }
            }
            catch (Exception e){
                //TODO:really?
            }
        }
        jsonWriter.writeCloseBracket();
    }

    public void saveJSON(Object src)
            throws JSONSerializationException{
        jsonWriter = JSONWriter.getJSONWriter();
        serialized.clear();

        saveObjectAsJSON(src);
    }

    public String JSONasString(){
        return jsonWriter.toString();
    }
}

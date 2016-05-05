package jsonbeans;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by Morozov Ivan on 07.03.2016.
 *
 * Class to make JSON from JavaBean class
 */
public class JSONEncoder {

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
                    try {
                        if (property == properties.getLast()) comma = false;

                        propertyName = property.getName();
                        Method readMethod = property.getReadMethod();

                        if (!JSONUtil.primitiveSet.contains(property.getPropertyType())) {

                            if (property instanceof IndexedPropertyDescriptor) {
                                saveArrayAsJSON((IndexedPropertyDescriptor) property, src);
                                if (comma) jsonWriter.writeComma();
                            } else {
                                if (readMethod != null) {
                                    Object obj = readMethod.invoke(src);

                                    if (!serialized.contains(obj) && obj != null) {
                                        jsonWriter.writeName(propertyName);
                                        saveObjectAsJSON(obj);
                                        if (comma) jsonWriter.writeComma();
                                    }
                                }
                            }
                        } else if (readMethod != null)
                            jsonWriter.writePair(propertyName, readMethod.invoke(src), comma);
                    } catch (Exception e) {
                        //ignore exceptions
                    }
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
        Method readMethod = indexedProp.getReadMethod();

        if (readMethod != null) {
            if (JSONUtil.primitiveArraysSet.contains(propertyType)) {
                if (propertyType == int[].class) jsonWriter.writeArrayOfPrimitives((int[]) readMethod.invoke(invoker));
                else if (propertyType == short[].class)
                    jsonWriter.writeArrayOfPrimitives((short[]) readMethod.invoke(invoker));
                else if (propertyType == byte[].class)
                    jsonWriter.writeArrayOfPrimitives((byte[]) readMethod.invoke(invoker));
                else if (propertyType == double[].class)
                    jsonWriter.writeArrayOfPrimitives((double[]) readMethod.invoke(invoker));
                else if (propertyType == float[].class)
                    jsonWriter.writeArrayOfPrimitives((float[]) readMethod.invoke(invoker));
                else if (propertyType == long[].class)
                    jsonWriter.writeArrayOfPrimitives((long[]) readMethod.invoke(invoker));
                else if (propertyType == char[].class)
                    jsonWriter.writeArrayOfPrimitives((char[]) readMethod.invoke(invoker));
                else if (propertyType == boolean[].class)
                    jsonWriter.writeArrayOfPrimitives((boolean[]) readMethod.invoke(invoker));
                else jsonWriter.writeArrayOfWrappers((Object[]) readMethod.invoke(invoker));
            } else {
                try {
                    Object[] objectArray = (Object[]) readMethod.invoke(invoker);

                    for (int i = 0; i < objectArray.length; i++) {
                        saveObjectAsJSON(objectArray[i]);

                        if (i != objectArray.length - 1) jsonWriter.write(',');
                    }
                } catch (Exception e) {
                    //TODO:really?
                }
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

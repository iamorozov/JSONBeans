package jsonbeans;

import beans.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.event.ChangeEvent;
import java.beans.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Morozov Ivan on 07.03.2016.
 */
public class JSONEncoder {

    private JSONWriter jsonWriter;

    private Set<Class> primitiveSet = new HashSet<Class>(
            Arrays.asList(new Class[] {
                    Integer.class, Double.class, Float.class,
                    Boolean.class, String.class, Class.class,
                    Byte.class, Short.class, Long.class,
                    Character.class, int.class, double.class,
                    float.class, boolean.class, byte.class,
                    short.class, long.class, char.class
            })
    );

    private Set<Object> serialized = new HashSet<Object>();

//    private Set<>

    private void saveObjectAsJSON(Object src) {
        try{
            if(!serialized.contains(src)){

                serialized.add(src);//put object into hashSet to prevent multiple serialization

                BeanInfo beanInfo = Introspector.getBeanInfo(src.getClass());
                PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();

                jsonWriter.writeOpenBrace();

                String propertyName;

                boolean comma = true;//to distinguish whether comma is needed after a pair or not

                for (int i = 0; i < properties.length; i++) {

                    if (i == properties.length - 1)
                        comma = false;

                    propertyName = properties[i].getName();

                    if (!primitiveSet.contains(properties[i].getPropertyType())){

                        if(properties[i] instanceof IndexedPropertyDescriptor){

                            Object[] propsArray = (Object[])properties[i].getReadMethod().invoke(src);

                            saveArrayAsJSON((IndexedPropertyDescriptor)properties[i], propsArray);
                        }
                        else {
                            Object obj = properties[i].getReadMethod().invoke(src);
                            if (!serialized.contains(obj)){
                                jsonWriter.writeName(propertyName);
                                saveObjectAsJSON(obj);
                            }
                        }
                    }
                    else
                        jsonWriter.writePair(propertyName, properties[i].getReadMethod().invoke(src), comma);
                }

                jsonWriter.writeCloseBrace();

                serialized.remove(src);
            }
        }
        catch (IntrospectionException e){
            e.printStackTrace();
        }
        catch (IllegalAccessException e){
            e.printStackTrace();
        }
        catch (InvocationTargetException e){
            e.printStackTrace();
        }
    }

    private void saveArrayAsJSON(IndexedPropertyDescriptor indexedProp, Object[] objectArray){

        jsonWriter.writeName(indexedProp.getName());
        jsonWriter.writeOpenBracket();

        if (primitiveSet.contains(indexedProp.getPropertyType()))
            for (int i = 0; i < objectArray.length; i++) {
                jsonWriter.writeValue(objectArray[i]);

                if(i != objectArray.length - 1) jsonWriter.write(',');
            }
        else
            for (int i = 0; i < objectArray.length; i++) {
                saveObjectAsJSON(objectArray[i]);

                if(i != objectArray.length - 1) jsonWriter.write(',');
            }

        jsonWriter.writeCloseBracket();
        jsonWriter.write(',');
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
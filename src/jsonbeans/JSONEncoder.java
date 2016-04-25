package jsonbeans;


import java.beans.*;
import java.util.*;

/**
 * Created by Morozov Ivan on 07.03.2016.
 *
 * Class to make JSON from JavaBean class
 */
public class JSONEncoder {

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

                            Object[] propsArray = (Object[])property.getReadMethod().invoke(src);

                            saveArrayAsJSON((IndexedPropertyDescriptor)property, propsArray);
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

    private void saveArrayAsJSON(IndexedPropertyDescriptor indexedProp, Object[] objectArray){

        jsonWriter.writeName(indexedProp.getName());
        jsonWriter.writeOpenBracket();

        if (JSONUtil.primitiveSet.contains(indexedProp.getPropertyType()))
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
        jsonWriter.writeComma();
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

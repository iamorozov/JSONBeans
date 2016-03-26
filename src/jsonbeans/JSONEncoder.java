package jsonbeans;

import beans.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSerializer;

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


    public static void main(String[] args) {

        Dolphin dolphin1 = new StripedDolphin();
        Dolphin dolphin2 = new DottetDolphin();
        Dolphin dolphin3 = new Dolphin();

        Trainer trainer1 = new Trainer();
        Trainer trainer2 = new Trainer();

        Dolphinarium dolphinarium = new Dolphinarium();

        dolphin1.setName("Amicus");
        dolphin2.setName("Fidget");
        dolphin3.setName("Flipper");

        dolphin1.setDolphinarium(dolphinarium);
        dolphin2.setDolphinarium(dolphinarium);
        dolphin3.setDolphinarium(dolphinarium);

        trainer1.setDolphins(new Dolphin[]{dolphin1, dolphin2, dolphin3});
        trainer2.setDolphins(new Dolphin[]{dolphin1, dolphin2});

        trainer1.setName("John");
        trainer2.setName("Sarah");

        dolphinarium.setFirstTrainer(trainer1);
        dolphinarium.setSecondTrainer(trainer2);

        JSONEncoder jsonEncoder = new JSONEncoder();


        try{

            /*My serializer*/
            jsonEncoder.saveJSON(dolphinarium);
//            System.out.println(jsonEncoder.jsonWriter);

            /*Google JSON Lib*/
//            Gson gson = new Gson();
//
//            String str = gson.toJson(dolphinarium);
//
//            System.out.println(str);


            /*java.Beans XMLEncoder*/
//            OutputStream outputStream = new FileOutputStream("out.xml");
//            XMLEncoder xmlEncoder = new XMLEncoder(outputStream);
//
//            xmlEncoder.writeObject(dolphinarium);
//            xmlEncoder.close();


            /*Jackson*/

        }
        catch (JSONSerializationException e){
            e.printStackTrace();
        }
//        catch (FileNotFoundException e){
//            e.printStackTrace();
//        }
    }
}

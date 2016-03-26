package jsonbeans;

import beans.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Morozov Ivan on 26.03.2016.
 */
public class JSONEncoderTest {

    /*Test fields*/
    Dolphin dolphin1 = new StripedDolphin();
    Dolphin dolphin2 = new DottetDolphin();
    Dolphin dolphin3 = new Dolphin();

    Trainer trainer1 = new Trainer();
    Trainer trainer2 = new Trainer();

    Dolphinarium dolphinarium = new Dolphinarium();

    @Test
    void buildObjectGraph(){

        dolphin1.setName("Amicus");
        dolphin2.setName("Fidget");
        dolphin3.setName("Flipper");

        dolphin1.setDolphinarium(null);
        dolphin2.setDolphinarium(null);
        dolphin3.setDolphinarium(null);

        trainer1.setDolphins(new Dolphin[]{dolphin1, dolphin2, dolphin3});
        trainer2.setDolphins(new Dolphin[]{dolphin1, dolphin2});

        trainer1.setName("John");
        trainer2.setName("Sarah");

        dolphinarium.setFirstTrainer(trainer1);
        dolphinarium.setSecondTrainer(trainer2);
    }


    @Test
    void jsonEncoderTest(){


        JSONEncoder jsonEncoder = new JSONEncoder();


        try{

            /*My serializer*/
            jsonEncoder.saveJSON(dolphinarium);
            System.out.println(jsonEncoder.JSONasString());

        }
        catch (JSONSerializationException e){
            e.printStackTrace();
        }

    }

    @Test
    void gsonTest(){
//        /*Google JSON Lib*/
//            Gson gson = new Gson();
//
//            String str = gson.toJson(dolphinarium);
//
//            System.out.println(str);
    }

    @Test
    void jacksonTest(){

        /*Jackson*/

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jacksonString = objectMapper.writeValueAsString(dolphinarium);
            System.out.println(jacksonString);
        }
        catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }

    @Test
    void xmlEncoderTest() {
        /*java.Beans XMLEncoder*/

        try {
            OutputStream outputStream = new FileOutputStream("out.xml");
            XMLEncoder xmlEncoder = new XMLEncoder(outputStream);

            xmlEncoder.writeObject(dolphinarium);
            xmlEncoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

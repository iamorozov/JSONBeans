package jsonbeans;

import beans.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class JacksonTest {


    /*Test fields*/
    Dolphin dolphin1 = new StripedDolphin();
    Dolphin dolphin2 = new DottetDolphin();
    Dolphin dolphin3 = new Dolphin();

    Trainer trainer1 = new Trainer();
    Trainer trainer2 = new Trainer();

    Dolphinarium dolphinarium = new Dolphinarium();

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
    public void jacksonTest(){

        buildObjectGraph();

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
}

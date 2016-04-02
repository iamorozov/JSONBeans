package jsonbeans;

import beans.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

public class JSONEncoderTest {

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

        dolphin1.setDolphinarium(dolphinarium);
        dolphin2.setDolphinarium(dolphinarium);
        dolphin3.setDolphinarium(dolphinarium);

        trainer1.setDolphins(new Dolphin[]{dolphin1, dolphin2, dolphin3});
        trainer2.setDolphins(new Dolphin[]{dolphin1, dolphin2});

        trainer1.setName("John");
        trainer2.setName("Sarah");

        dolphinarium.setFirstTrainer(trainer1);
        dolphinarium.setSecondTrainer(trainer2);
    }


    @Test
    public void jsonEncoderTest(){

        buildObjectGraph();
        JSONEncoder jsonEncoder = new JSONEncoder();

        Assert.assertNotNull(dolphinarium);

        try{

            /*My serializer*/
            jsonEncoder.saveJSON(dolphinarium);
            System.out.println(jsonEncoder.JSONasString());

        }
        catch (JSONSerializationException e){
            e.printStackTrace();
        }

    }
}

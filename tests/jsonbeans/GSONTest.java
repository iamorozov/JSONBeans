package jsonbeans;

import beans.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

public class GSONTest {

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
    public void gsonTest() {

        buildObjectGraph();

        /*Google JSON Lib*/

        GsonBuilder gb = new GsonBuilder();

        gb.setPrettyPrinting();
        Gson gson = gb.create();

        String str = gson.toJson(dolphinarium);


        Dolphinarium des = gson.fromJson(str, Dolphinarium.class);

        System.out.println(str);
    }

}

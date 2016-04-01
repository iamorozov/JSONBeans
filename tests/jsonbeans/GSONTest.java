package jsonbeans;

import beans.*;
import org.junit.Test;

public class GSONTest {

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
    void gsonTest(){
//        /*Google JSON Lib*/
//            Gson gson = new Gson();
//
//            String str = gson.toJson(dolphinarium);
//
//            System.out.println(str);
    }

}

package jsonbeans;

import beans.*;
import org.junit.Test;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class XMLEncoderTest {

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
    public void xmlEncoderTest() {
        /*java.Beans XMLEncoder*/

        buildObjectGraph();

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

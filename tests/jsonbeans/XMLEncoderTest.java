package jsonbeans;

import beans.*;
import org.junit.Test;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class XMLEncoderTest {


    @Test
    public void xmlEncoderTest() {
        /*java.Beans XMLEncoder*/

        Dolphinarium dolphinarium = DolphinsGraph.buildDolphinarium();

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

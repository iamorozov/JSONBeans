package jsonbeans;

import beans.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

public class JSONEncoderTest {

    @Test
    public void jsonEncoderTest(){

        Dolphinarium dolphinarium = DolphinsGraph.buildDolphinarium();

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

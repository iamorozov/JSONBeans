package jsonbeans;

import beans.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

public class JSONEncoderTest {

    JSONEncoder jsonEncoder = new JSONEncoder();
    TestBeanFactory factory = new TestBeanFactory();

    @Test
    public void jsonEncoderDolphinariumTest(){

        Dolphinarium dolphinarium = (Dolphinarium)factory.createTestBean("Dolphinarium");

        try{
            jsonEncoder.saveJSON(dolphinarium);
            System.out.println(jsonEncoder.JSONasString());
        }
        catch (JSONSerializationException e){
            e.printStackTrace();
        }

    }
}

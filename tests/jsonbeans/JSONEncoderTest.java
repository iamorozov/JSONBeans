package jsonbeans;

import beans.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

public class JSONEncoderTest {

    @Test
    public void jsonEncoderDolphinariumTest(){

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

    @Test
    public void jsonEncoderSimpleBeanTest(){
        String s = TestClassFactory.buildTestJSONString();

        System.out.println(s);
    }

    @Test
    public void castingTest(){

        TestBean bean = TestClassFactory.buildTestBean();

        bean.setIntArray(1, 1);
    }
}

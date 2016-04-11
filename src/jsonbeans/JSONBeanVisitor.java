package jsonbeans;

import com.sun.xml.internal.ws.encoding.soap.DeserializationException;
import jsonbeans.recognizer.JSONBaseVisitor;
import jsonbeans.recognizer.JSONParser;
import sun.security.krb5.internal.crypto.Des;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * Created by Morozov Ivan on 10.04.2016.
 *
 * Class to traverse parse tree and create object
 */
public class JSONBeanVisitor extends JSONBaseVisitor {

    Class desType;
    Object toConstruct;

    JSONBeanVisitor (Class classOfDeserialization)
            throws JSONDeserializationException{
        desType = classOfDeserialization;
        initializeObject();
    }

    void initializeObject() throws JSONDeserializationException{
        try {
            toConstruct = Class.forName(desType.getName()).newInstance();
        }
        catch (Exception e){
            throw new JSONDeserializationException();
        }
    }


    @Override
    public Object visitJson(JSONParser.JsonContext ctx) {

        toConstruct = visitObject(ctx.object());

        return toConstruct;
    }

    @Override
    public Object visitObject(JSONParser.ObjectContext ctx){

        try{
            BeanInfo beanInfo = Introspector.getBeanInfo(desType);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property: propertyDescriptors) {
                if (JSONUtil.primitiveSet.contains(property.getPropertyType())){

                }
            }
        }
        catch (IntrospectionException e){
            throw new RuntimeException();
        }

        return super.visitObject(ctx);
    }

    @Override
    public Object visitPair(JSONParser.PairContext ctx) {
        return super.visitPair(ctx);
    }

    @Override
    public Object visitArray(JSONParser.ArrayContext ctx) {
        return super.visitArray(ctx);
    }

    @Override
    public Object visitValue(JSONParser.ValueContext ctx) {
        return super.visitValue(ctx);
    }
}

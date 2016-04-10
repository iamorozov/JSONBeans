package jsonbeans;

import jsonbeans.recognizer.JSONBaseVisitor;
import jsonbeans.recognizer.JSONParser;

/**
 * Created by Morozov Ivan on 10.04.2016.
 *
 *
 */
public class JSONBeanVisitor extends JSONBaseVisitor {
    @Override
    public Object visitJson(JSONParser.JsonContext ctx) {
        return super.visitJson(ctx);
    }

    @Override
    public Object visitObject(JSONParser.ObjectContext ctx) {
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

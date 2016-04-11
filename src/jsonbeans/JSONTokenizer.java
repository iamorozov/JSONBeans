package jsonbeans;

import java.util.StringTokenizer;

/**
 * Created by Morozov Ivan on 07.04.2016.
 *
 * Class used for lexical division of json object
 */

public class JSONTokenizer {

    StringTokenizer tokenizer;

    public JSONTokenizer(String jsonString){
        jsonString = makeUnpretty(jsonString);
        jsonString = trimBraces(jsonString);

        tokenizer = new StringTokenizer(jsonString, ",");
    }

    String trimBraces(String jsonObj){
        if(jsonObj.charAt(0) == '{' && jsonObj.charAt(jsonObj.length() - 1) == '}')
            return jsonObj.substring(1, jsonObj.length() - 1);

        return jsonObj;
    }

    String makeUnpretty(String jsonObj){
        jsonObj = jsonObj.replace(" ", "");
        jsonObj = jsonObj.replace("\n", "");

        return jsonObj;
    }

    public boolean hasMoreTokens(){
        return tokenizer.hasMoreTokens();
    }

    public String nextToken(){
        return tokenizer.nextToken();
    }
}

package jsonbeans;

/**
 * Created by Morozov Ivan on 19.03.2016.
 *
 * Class to read Beans from JSON
 */
public class JSONDecoder{

    String jsonString;
    Class desClass;

    JSONDecoder(String jsonString, Class classOfDeserialization){
        this.jsonString = jsonString;
        desClass = classOfDeserialization;
    }

    public Object readJSON()
            throws JSONDeserializationException{


        return null;
    }
}

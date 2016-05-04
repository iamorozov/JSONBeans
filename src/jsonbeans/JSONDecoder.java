package jsonbeans;

/**
 * Created by Morozov Ivan on 19.03.2016.
 *
 * Class to read Beans from JSON
 */
public class JSONDecoder{

    private JSONTokenizer tokenizer;

    /**
     * @param jsonString - A string with JSON representation of object
     * @throws JSONDeserializationException
     */
    public JSONDecoder(String jsonString) throws JSONDeserializationException {
        tokenizer = new JSONTokenizer(jsonString);
    }

    /**
     * @return deserialized object
     * @throws JSONDeserializationException
     */
    public Object readJSON() throws JSONDeserializationException {
        return tokenizer.readObject();
    }
}

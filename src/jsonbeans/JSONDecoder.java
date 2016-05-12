package jsonbeans;

/**
 * Class to read Beans from JSON
 */
public class JSONDecoder{

    private JSONTokenizer tokenizer;

    /**
     * @param jsonString - A string with JSON representation of object
     * @throws JSONDeserializationException - Deserialization problem
     * encapsulated in exception
     */
    public JSONDecoder(String jsonString) throws JSONDeserializationException {
        tokenizer = new JSONTokenizer(jsonString);
    }

    /**
     * @return deserialized object
     * @throws JSONDeserializationException  - Deserialization problem
     * encapsulated in exception
     */
    public Object readJSON() throws JSONDeserializationException {
        return tokenizer.readObject();
    }
}

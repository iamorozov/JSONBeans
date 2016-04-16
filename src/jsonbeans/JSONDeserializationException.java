package jsonbeans;

/**
 * An exception for errors in JSON string
 */
public class JSONDeserializationException extends Exception {

    public JSONDeserializationException(String message) {
        super(message);
    }

    public JSONDeserializationException(String message, int line) {
        super(message + ", line : " + line);
    }
}

package jsonbeans;

/**
 * Class for JSON serialization exceptions
 */
public class JSONSerializationException extends Exception {

    public JSONSerializationException() {
        super("Some JSON serialization problem");
    }

    public JSONSerializationException(String message) {
        super(message);
    }
}

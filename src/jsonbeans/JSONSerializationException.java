package jsonbeans;

/**
 * Class for JSON serialization exceptions
 */
public class JSONSerializationException extends Exception {

    public JSONSerializationException() {
    }

    public JSONSerializationException(String message) {
        super(message);
    }
}

package jsonbeans;

/**
 * Created by Morozov Ivan on 07.03.2016.
 *
 * Class for JSON serialization exceptions
 */
public class JSONSerializationException extends Exception {

    public JSONSerializationException() {
    }

    public JSONSerializationException(String message) {
        super(message);
    }
}

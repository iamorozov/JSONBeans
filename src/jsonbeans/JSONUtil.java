package jsonbeans;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Morozov Ivan on 10.04.2016.
 *
 * Contains some utility information
 */
public class JSONUtil {
    public static Set<Class> primitiveSet = new HashSet<>(
            Arrays.asList(new Class[] {
                    Integer.class, Double.class, Float.class,
                    Boolean.class, String.class, Class.class,
                    Byte.class, Short.class, Long.class,
                    Character.class, int.class, double.class,
                    float.class, boolean.class, byte.class,
                    short.class, long.class, char.class
            })
    );
}

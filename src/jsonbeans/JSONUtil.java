package jsonbeans;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Contains some utility information
 */
class JSONUtil {
    static Set<Class> primitiveSet = new HashSet<>(
            Arrays.asList(new Class[]{
                    Integer.class, Double.class, Float.class,
                    Boolean.class, String.class, Class.class,
                    Byte.class, Short.class, Long.class,
                    Character.class, int.class, double.class,
                    float.class, boolean.class, byte.class,
                    short.class, long.class, char.class
            })
    );

    static Set<Class> primitiveArraysSet = new HashSet<>(
            Arrays.asList(new Class[]{
                    Integer[].class, Double[].class, Float[].class,
                    Boolean[].class, String[].class, Class[].class,
                    Byte[].class, Short[].class, Long[].class,
                    Character[].class,
                    int[].class, double[].class,
                    float[].class, boolean[].class, byte[].class,
                    short[].class, long[].class, char[].class
            })
    );

    static Set<Class> numberTypes = new HashSet<>(
            Arrays.asList(new Class[]{
                    Integer.class, Byte.class, Short.class,
                    Double.class, Float.class, Long.class,
                    int.class, byte.class,
                    short.class, long.class,
                    double.class, float.class
            })
    );

    static Set<Class> logicalTypes = new HashSet<>(
            Arrays.asList(new Class[]{
                    Boolean.class, boolean.class
            })
    );

    static Set<Class> characterSequenceTypes = new HashSet<>(
            Arrays.asList(new Class[]{
                    String.class, Character.class, char.class
            })
    );
}

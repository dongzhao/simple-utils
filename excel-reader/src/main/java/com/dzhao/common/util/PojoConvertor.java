package com.dzhao.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by dzhao on 27/07/2015.
 */
public class PojoConvertor {
    static boolean DEFAULT_BOOLEAN = false;
    static byte DEFAULT_BYTE = 0;
    static short DEFAULT_SHORT = 0;
    static int DEFAULT_INT = 0;
    static long DEFAULT_LONG = 0L;
    static float DEFAULT_FLOAT = 0.0f;
    static double DEFAULT_DOUBLE = 0.0d;
    static double DEFAULT_CHAR = '\u0000';
    static String DEFAULT_STR = null;

    private static Logger logger = LoggerFactory.getLogger(PojoConvertor.class);

    private PojoConvertor(){}


    private static boolean toBooleanValue(Object obj){
        if(obj==null){
            return false;
        }else if(String.valueOf(obj).equalsIgnoreCase("y")){
            return true;
        }else if(String.valueOf(obj).equalsIgnoreCase("yes")){
            return true;
        }else if(String.valueOf(obj).equalsIgnoreCase("true")){
            return true;
        }else{
            return false;
        }
    }

    public static Object convertTo(Field field, Object obj) {
        if (boolean.class.isAssignableFrom(field.getType())) {
            return toBooleanValue(obj);
        } else if (Boolean.class.isAssignableFrom(field.getType())) {
            return obj==null ? Boolean.valueOf(null) : Boolean.valueOf(toBooleanValue(obj));
        } else if (String.class.isAssignableFrom(field.getType())) {
            return String.valueOf(obj);
        } else if (int.class.isAssignableFrom(field.getType())) {
            return Integer.valueOf(String.valueOf(obj)).intValue();
        } else if (Integer.class.isAssignableFrom(field.getType())) {
            return obj==null ? Integer.valueOf(DEFAULT_INT) : Integer.valueOf(String.valueOf(obj));
        } else if (long.class.isAssignableFrom(field.getType())) {
            return Long.valueOf(String.valueOf(obj)).longValue();
        } else if (Long.class.isAssignableFrom(field.getType())) {
            return obj==null ? Long.valueOf(DEFAULT_LONG) : Long.valueOf(String.valueOf(obj));
        } else if (Map.class.isAssignableFrom(field.getType())) {
            return obj;
        }else {
            throw new UnsupportedOperationException("unsupported type");
        }
    }
}

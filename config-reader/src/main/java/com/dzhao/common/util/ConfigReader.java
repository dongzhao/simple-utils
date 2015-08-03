package com.dzhao.common.util;

import com.dzhao.common.util.annotation.Config;
import com.dzhao.common.util.annotation.ConfigField;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;

public class ConfigReader {
    static boolean DEFAULT_BOOLEAN = false;
    static byte DEFAULT_BYTE = 0;
    static short DEFAULT_SHORT = 0;
    static int DEFAULT_INT = 0;
    static long DEFAULT_LONG = 0L;
    static float DEFAULT_FLOAT = 0.0f;
    static double DEFAULT_DOUBLE = 0.0d;
    static double DEFAULT_CHAR = '\u0000';
    static String DEFAULT_STR = null;

    private static String DOT = ".";

    private final String filePath;

    private final PropertiesConfiguration configuration;

    public ConfigReader(String filePath) throws ConfigurationException {
        this.filePath = filePath;
        URL url = this.getClass().getClassLoader().getResource(filePath);
        this.configuration = new PropertiesConfiguration(url);
    }

    public <T> T getConfig(Class<T> clazz){
        T configuration = null;
        try {
            configuration = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Config config = clazz.getAnnotation(Config.class);
        if(config==null)
            throw new RuntimeException("Unsupported object");

        StringBuilder configKeyPrefix = new StringBuilder();

        String prefix = config.prefix().trim();
        if(!prefix.isEmpty()){
            configKeyPrefix.append(prefix + DOT);
        }

        for(Field field : clazz.getDeclaredFields())  {
            StringBuilder configKey = new StringBuilder(configKeyPrefix);
            ConfigField configField = field.getAnnotation(ConfigField.class);
            if(configField==null)
                continue;
            String fieldName = configField.name().trim();
            if(fieldName.isEmpty()){
                configKey.append(format(field.getName(), DOT));
            }else{
                configKey.append(fieldName);
            }
            field.setAccessible(true);
            Object objValue = getValue(configKey.toString(), field);
            try {
                field.set(configuration, objValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            field.setAccessible(false);
        }
        return configuration;
    }

    private String format(String value, String split) {
        StringBuilder output = new StringBuilder();
        String[] tokens = StringUtils.splitByCharacterTypeCamelCase(value);
        for (String token : tokens) {
            if (output.length() > 0) {
                output.append(split);
            }
            output.append(token.toLowerCase().trim());
        }
        return output.toString();
    }

    public Object getValue(String key, Field field) {
        synchronized (configuration) {
            if (int.class.isAssignableFrom(field.getType())) {
                return configuration.getInt(key);
            } else if (boolean.class.isAssignableFrom(field.getType())) {
                return configuration.getBoolean(key);
            } else if (long.class.isAssignableFrom(field.getType())) {
                return configuration.getLong(key);
            } else if (short.class.isAssignableFrom(field.getType())) {
                return configuration.getShort(key);
            } else if (byte.class.isAssignableFrom(field.getType())) {
                return configuration.getByte(key);
            } else if (float.class.isAssignableFrom(field.getType())) {
                return configuration.getFloat(key);
            } else if (double.class.isAssignableFrom(field.getType())) {
                return configuration.getDouble(key);
            } else if (char.class.isAssignableFrom(field.getType())) {
                return configuration.getString(key).toCharArray()[0];
            } else if (String[].class.isAssignableFrom(field.getType())) {
                return configuration.getStringArray(key);
            } else if (String.class.isAssignableFrom(field.getType())) {
                return configuration.getString(key, null);
            } else if (Integer.class.isAssignableFrom(field.getType())) {
                return configuration.getInteger(key, Integer.valueOf(DEFAULT_INT));
            } else if (Long.class.isAssignableFrom(field.getType())) {
                return configuration.getLong(key, Long.valueOf(DEFAULT_LONG));
            } else if (Double.class.isAssignableFrom(field.getType())) {
                return configuration.getDouble(key, Double.valueOf(DEFAULT_DOUBLE));
            } else if (Float.class.isAssignableFrom(field.getType())) {
                return configuration.getFloat(key, Float.valueOf(DEFAULT_FLOAT));
            } else if (Boolean.class.isAssignableFrom(field.getType())) {
                return configuration.getBoolean(key, Boolean.valueOf(null));
            } else if (Byte.class.isAssignableFrom(field.getType())) {
                return configuration.getByte(key, Byte.valueOf(DEFAULT_BYTE));
            } else if (Short.class.isAssignableFrom(field.getType())) {
                return configuration.getShort(key, Short.valueOf(DEFAULT_SHORT));
            } else if (List.class.isAssignableFrom(field.getType())) {
                return configuration.getList(key, null);
            } else {
                throw new UnsupportedOperationException("not supported the value type");
            }
        }
    }
}

package com.avanzarit.apps.gst.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SPADHI on 5/5/2017.
 */
public class Utils {

    public  static final Map<String, String> country;
    public static final Map<String,String> city;
    public static final Map<String,String> region;
    public static final Map<String,String> language;

    static
    {
        country = new HashMap<String, String>();
        country.put("001", "India");
    }

    static
    {
        city = new HashMap<String, String>();
        city.put("002", "West Bengal");
    }

    static
    {
        region = new HashMap<String, String>();
        region.put("003", "Kolkata");
        region.put("004", "Durgaput");
    }

    static
    {
        language = new HashMap<String, String>();
        language.put("005", "Bengali");
    }

    /**
     * @return null safe set
     */
    public static List<Field> findFields(Class<?> classs, Class<? extends Annotation> ann) {
        List<Field> list = new ArrayList<>();
        Class<?> c = classs;
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(ann)) {
                    list.add(field);
                }
            }
            c = c.getSuperclass();
        }
        return list;
    }
}

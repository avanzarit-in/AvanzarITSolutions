package com.avanzarit.apps.gst.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Utils {

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

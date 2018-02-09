package com.avanzarit.apps.gst.utils;

import com.avanzarit.apps.gst.annotations.TabInfo;

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

    public static List<Field> findFieldsForTabs(Class<?> classs, List<String> values) {
        List<Field> list = new ArrayList<>();
        Class<?> c = classs;
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(TabInfo.class)) {
                    TabInfo tabInfo=field.getAnnotation(TabInfo.class);
                    String tabName=tabInfo.tabName();
                    if(values.contains(tabName)){
                        list.add(field);
                    }
                }
            }
            c = c.getSuperclass();
        }
        return list;
    }
}

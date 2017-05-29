package com.avanzarit.apps.gst.utils;

import java.util.HashMap;
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
}

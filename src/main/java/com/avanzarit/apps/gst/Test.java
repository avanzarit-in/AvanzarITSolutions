package com.avanzarit.apps.gst;

/**
 * Created by AVANZAR on 6/5/2017.
 */
public class Test {
    public static void main(String[] arg) {
        String password = Long.toHexString(Double.doubleToLongBits(Math.random()));
        System.out.println(password);
    }
}

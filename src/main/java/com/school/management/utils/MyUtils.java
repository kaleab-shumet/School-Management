package com.school.management.utils;

public class MyUtils {

    public static int parseToInt(String stringToParse, int defaultValue) {
        try {
            return Integer.parseInt(stringToParse);
        } catch(NumberFormatException ex) {
            return defaultValue; //Use default value if parsing failed
        }
    }


    public static long parseToLong(String stringToParse, long defaultValue) {
        try {
            return Long.parseLong(stringToParse);
        } catch(NumberFormatException ex) {
            return defaultValue; //Use default value if parsing failed
        }
    }
}

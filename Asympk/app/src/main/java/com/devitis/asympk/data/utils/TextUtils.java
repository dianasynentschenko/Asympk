package com.devitis.asympkfinalversion.data.utils;

/**
 * Created by Diana on 10.05.2019.
 */

public class TextUtils {

    /**
     * check string for null
     * @param string
     * @return
     */
    public static boolean isValidString(String string) {

        if (string != null
                && string.length() > 0
                && !string.isEmpty()
                && !string.equalsIgnoreCase("")) {
            return true;
        }

        return false;
    }

    /**
     * cut string value
     * for 3 symbol after ,
     * @param value
     * @return
     */
    public String cutStringValue(String value) {

        return String.valueOf(((double) Math.round(Double.valueOf(value) * 1000d) / 1000d));

    }
}

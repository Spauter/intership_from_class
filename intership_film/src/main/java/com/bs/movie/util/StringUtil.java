package com.bs.movie.util;

public class StringUtil {
    public static boolean checkNull(String... params) {
        if (params == null || params.length <= 0) {
            return true;
        }
        for (String str : params) {
            if (str == null || "".equals(str)) {
                return true;
            }
        }
        return false;
    }


    public static String objToString(Object obj) {
        if (null == obj) {
            return "";
        }
        return obj.toString();
    }
}

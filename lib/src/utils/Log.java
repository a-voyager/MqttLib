package utils;

import constant.Constants;

/**
 * Created by wuhaojie on 17-5-25.
 */
public class Log {

    private Log() {
    }

    public static void d(String message) {
        if (Constants.DEBUG)
            System.out.println(message);
    }

    public static void e(Exception e) {
        if (Constants.DEBUG)
            e.printStackTrace();
    }

}

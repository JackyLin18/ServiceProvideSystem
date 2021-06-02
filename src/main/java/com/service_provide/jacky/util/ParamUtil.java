package com.service_provide.jacky.util;

public class ParamUtil {
    private ParamUtil() {
    }

    /**
     * @Author Jacky
     * @Param params 需要判断的参数
     * @Description 判断参数是否为空，为空返回 true,不为空返回 false
     **/
    public static boolean isParamNull(String... params) {
        for (String param : params) {
            if (param == null || "".equals(param)) {
                return true;
            }
        }
        return false;
    }
}

package cn.zhh.admin.util;

import cn.zhh.admin.entity.JobLog;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class AutoGenSetterCodeUtils {

    public static void main(String[] args) {
        Class clazz = JobLog.class;
        StringBuilder sb = new StringBuilder();
        String className = clazz.getSimpleName();
        sb.append(className).append(" ").append(lowerFirst(className))
                .append(" = new ").append(className).append("();").append(LF);
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> Modifier.isPrivate(field.getModifiers()) && !Modifier.isStatic(field.getModifiers()))
                .forEach(field -> {
                    sb.append(lowerFirst(className)).append(".")
                            .append(upperFirstAndAddPre(field.getName(), "set")).append("();")
                            .append(LF);
                });
        System.out.println(sb.toString());
    }

    private static final String LF = "\n";

    public static String upperFirstAndAddPre(String str, String preString) {
        return str != null && preString != null ? preString + upperFirst(str) : null;
    }

    public static String upperFirst(String str) {
        if (null == str) {
            return null;
        } else {
            if (str.length() > 0) {
                char firstChar = str.charAt(0);
                if (Character.isLowerCase(firstChar)) {
                    return Character.toUpperCase(firstChar) + str.substring(1);
                }
            }

            return str;
        }
    }

    private static String lowerFirst(String str) {
        if (null == str) {
            return null;
        } else {
            if (str.length() > 0) {
                char firstChar = str.charAt(0);
                if (Character.isUpperCase(firstChar)) {
                    return Character.toLowerCase(firstChar) + str.substring(1);
                }
            }

            return str;
        }
    }
}

package lab.utils;

import java.util.regex.Pattern;

public class DataTypeChecker {
    public static boolean isNumber(String para){
        return Pattern.matches("\\d+", para);
    }
}

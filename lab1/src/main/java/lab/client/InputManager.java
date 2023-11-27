package lab.client;

import java.util.ArrayList;
import java.util.List;

public class InputManager {
    // 不支持在字符串中包含转义字符和双引号，允许包含空格，此时需要用双引号括起来。
    public List<String> splitInputStr(String inputStr){
        String trimmedStr = inputStr.trim();
        int strLen = trimmedStr.length();
        boolean inQuotation = false;
        String currentParam = "";

        List<String> result = new ArrayList<>();
        for(int i = 0; i < strLen; i++){
            char currentChar = trimmedStr.charAt(i);
            if(inQuotation){
                if(currentChar == '"'){
                    inQuotation = false;
                    result.add(currentParam);
                    currentParam = "";
                } else {
                    currentParam += currentChar;
                }
            } else {
                if(currentChar == '"'){
                    if(currentParam.length() != 0) return result;
                    inQuotation = true;
                } else if(currentChar == ' '){
                    if(currentParam.length() != 0){
                        result.add(currentParam);
                        currentParam = "";
                    }
                } else {
                    currentParam += currentChar;
                }
            }
        }
        if(inQuotation) return result;
        if(currentParam.length() != 0){
            result.add(currentParam);
        }
        return result;
    }

    public static void main(String[] args) {

    }
}

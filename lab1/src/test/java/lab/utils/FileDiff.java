package lab.utils;

import java.util.List;

public class FileDiff {
    public static boolean compare(String filePath1, String filePath2){
        List<String> lines1 = Utils.getFileLines(filePath1, "UTF-8");
        List<String> lines2 = Utils.getFileLines(filePath2, "UTF-8");
        int lineNum = lines1.size();
        if(lines2.size() != lineNum){
            System.out.println("file1: " + lineNum);
            System.out.println("file2: " + lines2.size());
            return false;
        }
        for(int i = 0; i < lineNum; i++){
            String line1 = lines1.get(i);
            String line2 = lines2.get(i);
            if(!line1.equals(line2)){
                System.out.println("line1: " + line1);
                System.out.println("line2: " + line2);
                return false;
            }
        }
        return true;
    }
}

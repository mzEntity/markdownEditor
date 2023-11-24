package lab1.utils;


import java.nio.file.Paths;

public class Utils {
    public static String getNormalizedAbsolutePath(String path){
        return Paths.get(path).toAbsolutePath().normalize().toString();
    }

    public static void main(String[] args) {
        System.out.println(Utils.getNormalizedAbsolutePath("./src/lab1/client"));
    }
}

package lab.utils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String getNormalizedAbsolutePath(String path){
        return Paths.get(path).toAbsolutePath().normalize().toString();
    }

    public static String getRelativePath(String absolutePath, String basePath) {
        Path absolute = Paths.get(absolutePath).normalize();
        Path base = Paths.get(basePath).normalize();

        Path relative = base.relativize(absolute);
        return relative.toString();
    }

    public static String processPath(String path){
        String newPath = path.replace('/', '_').replace('\\', '_');
        return newPath;
    }

    public static List<String> getFileLines(String filePath, String charset){
        List<String> content = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), charset))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void saveFileLines(String filePath, List<String> content){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            for (String str : content) {
                writer.write(str);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveObject(String filePath, Serializable serializable){
        try {
            // 创建文件输出流
            FileOutputStream fileOut = new FileOutputStream(filePath);
            // 创建对象输出流
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            // 将对象写入输出流
            objectOut.writeObject(serializable);
            // 关闭输出流
            objectOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object readObject(String filePath){
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            Object obj = in.readObject();
            in.close();
            fileIn.close();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void cleanFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        cleanFolder(file.getAbsolutePath());
                        file.delete();
                    } else {
                        file.delete();
                    }
                }
            }
        }
    }

    public static String getFormattedTime(LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
        String formattedDateTime = time.format(formatter);
        return formattedDateTime;
    }

    public static LocalDateTime getLocalTime(String formattedTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(formattedTime, formatter);
        return localDateTime;
    }

    public static String getCurrentTime(){
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = Utils.getFormattedTime(now);
        return formattedDateTime;
    }

    public static void main(String[] args) {
        Utils.cleanFolder("./lab1testcase111");
    }
}

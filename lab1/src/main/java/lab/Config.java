package lab;


import lab.utils.Utils;

import java.io.File;

public class Config {
    public static String separator = "/";
    public static String tempFolderPathRelative = "./tmp";
    public static String mementoFileName = "memento";
    public static String statsFileName = "stats.txt";

    public static String currentWorkDirectoryPathAbsolute = Utils.getNormalizedAbsolutePath(".");
    public static String mementoFilePathRelative = tempFolderPathRelative + separator + mementoFileName;
    public static String logFileSuffix = ".log";
    public static String statsFilePathRelative = tempFolderPathRelative + separator + statsFileName;

    public static boolean enableMemento = true;

    public static boolean interactionMode = true;
    public static String interactPrompt = "> ";

    public static void setup(){
        File f = new File(tempFolderPathRelative);
        if(!f.exists()){
            f.mkdir();
        }
    }
}

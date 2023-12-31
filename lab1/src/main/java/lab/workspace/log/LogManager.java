package lab.workspace.log;

import lab.Config;
import lab.memento.Retainable;
import lab.utils.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogManager implements Retainable {

    private List<String> log;
    private String logFilePath;
    private int writeStartIndex;


    public LogManager(String filePath) {
        this.log = new ArrayList<>();
        String relativePath = Utils.getRelativePath(filePath, Config.currentWorkDirectoryPathAbsolute);
        String logFileName = Utils.processPath(relativePath);
        this.logFilePath = Config.tempFolderPathRelative + Config.separator + logFileName + Config.logFileSuffix;
        this.writeStartIndex = 0;
    }


    public void info(String msg){
        String logMsg = Utils.getCurrentTime() + " " + msg;
        this.log.add(logMsg);
    }

    public void writeLog(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.logFilePath, true))){
            int logCount = this.log.size();
            for(int i = this.writeStartIndex; i < logCount; i++){
                writer.write(this.log.get(i));
                writer.newLine();
            }
            this.writeStartIndex = logCount;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getLog(int logCount){
        int endIndex = this.log.size();
        if(logCount == -1){
            return this.log.subList(0, endIndex);
        }
        int startIndex = endIndex - logCount;
        if(startIndex < 0) startIndex = 0;
        return this.log.subList(startIndex, endIndex);
    }
}

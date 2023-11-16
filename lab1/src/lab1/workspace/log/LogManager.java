package lab1.workspace.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogManager {

    private List<String> log;
    private String logFilePath;
    private int writeStartIndex;


    public LogManager(String logFilePath) {
        this.log = new ArrayList<>();
        this.logFilePath = logFilePath;
        this.writeStartIndex = 0;
    }


    public void info(String msg){
        String logMsg = this.getCurrentTime() + " " + msg;
        this.log.add(logMsg);
    }

    public void writeLog(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.logFilePath, true));
            int logCount = this.log.size();
            for(int i = this.writeStartIndex; i < logCount; i++){
                writer.write(this.log.get(i));
                writer.newLine();
            }
            this.writeStartIndex = logCount;
            writer.close();
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



    public String getFormattedTime(LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
        String formattedDateTime = time.format(formatter);
        return formattedDateTime;
    }

    private String getCurrentTime(){
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = this.getFormattedTime(now);
        return formattedDateTime;
    }
}

package lab1.workspace.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StatsManager {
    private String statFilePath;
    private String logFilePath;
    private LocalDateTime sessionStartTime;

    private String currentWorkingFilePath;
    private LocalDateTime currentWorkingFileStartTime;

    public String getCurrentWorkingFilePath() {
        return currentWorkingFilePath;
    }

    private List<String> workedFileNameList;
    private List<Duration> workedFileDuration;

    private int statsWriteIndex;

    public StatsManager(String filePath) {
        this.statFilePath = filePath + ".stats";
        this.logFilePath = filePath + ".logfile";

        this.sessionStart();
        this.currentWorkingFilePath = null;
        this.currentWorkingFileStartTime = null;

        this.workedFileNameList = new ArrayList<>();
        this.workedFileDuration = new ArrayList<>();
        this.statsWriteIndex = 0;

        this.fileWorkStart(filePath);
    }

    public void sessionStart(){
        this.sessionStartTime = LocalDateTime.now();
        this.writeSessionStart();
    }

    public void fileWorkStart(String filePath){
        this.currentWorkingFilePath = filePath;
        currentWorkingFileStartTime = LocalDateTime.now();
    }

    public void fileWorkEnd(){
        if(this.currentWorkingFileStartTime == null) return;

        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(this.currentWorkingFileStartTime, endTime);
        this.workedFileNameList.add(this.currentWorkingFilePath);
        this.workedFileDuration.add(duration);

        this.currentWorkingFilePath = null;
        this.currentWorkingFileStartTime = null;
    }

    public String sessionInfo(){
        return "session start at " + this.getFormattedTime(this.sessionStartTime);
    }

    public void writeSessionStart(String filePath){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
            String info = this.sessionInfo();
            writer.write(info);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSessionStart(){
        this.writeSessionStart(this.statFilePath);
        this.writeSessionStart(this.logFilePath);
    }
    public void writeSessionStat(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.statFilePath, true))){
            List<String> allStats = this.getAllFileStats();
            int statsCount = allStats.size();
            for(int i = this.statsWriteIndex; i < statsCount; i++){
                writer.write(allStats.get(i));
                writer.newLine();
            }
            this.statsWriteIndex = statsCount;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String durationToString(Duration duration){
        long minutes = duration.toMinutes();
        if(minutes < 10){
            long seconds = duration.toSeconds();
            seconds = seconds - minutes * 60;
            return minutes + "分钟" + seconds + "秒";
        } else if(minutes >= 60){
            long hours = duration.toHours();
            long minutesLeft = minutes - 60 * hours;
            if(minutesLeft == 0){
                return hours + "小时";
            } else {
                return hours + "小时" + minutesLeft + "分钟";
            }
        } else {
            return minutes + "分钟";
        }
    }

    public List<String> getAllStat(){
        int workedFileCount = this.workedFileNameList.size();
        List<String> allStats = new ArrayList<>();
        allStats.add(this.sessionInfo());
        List<String> allFileStats = this.getAllFileStats();
        allStats.addAll(allFileStats);
        return allStats;
    }

    private List<String> getAllFileStats(){
        int workedFileCount = this.workedFileNameList.size();
        List<String> allStats = new ArrayList<>();
        for(int i = 0; i < workedFileCount; i++){
            String statLog = this.workedFileNameList.get(i) + " " + this.durationToString(this.workedFileDuration.get(i));
            allStats.add(statLog);
        }
        return allStats;
    }

    public String getCurrentStat(){
        if(this.currentWorkingFileStartTime == null){
            System.out.println("You have not start working on any file yet.");
            return null;
        }
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(this.currentWorkingFileStartTime, endTime);
        String result = this.currentWorkingFilePath + " " + this.durationToString(duration);
        return result;
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

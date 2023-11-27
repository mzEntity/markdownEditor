package lab1.workspace.log;

import lab1.memento.Retainable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatsManager implements Retainable {
    private String statFilePath;
    private LocalDateTime sessionStartTime;

    private HashMap<String, LocalDateTime> currentWorkingFileStartTime;

    private List<String> workedFileNameList;
    private List<Duration> workedFileDuration;

    private int statsWriteIndex;

    public StatsManager() {
        this.statFilePath = "./stats.txt";
        this.currentWorkingFileStartTime = new HashMap<>();

        this.workedFileNameList = new ArrayList<>();
        this.workedFileDuration = new ArrayList<>();
        this.statsWriteIndex = 0;
    }

    public void initStatsManager(){
        this.sessionStart();
        this.fileWorkStart(this.statFilePath);
    }


    private void sessionStart(){
        this.sessionStartTime = LocalDateTime.now();
        this.writeSessionStart();
    }

    public void fileWorkStart(String filePath){
        this.currentWorkingFileStartTime.put(filePath, LocalDateTime.now());
    }

    public void fileWorkEnd(String filePath){
        if(!this.currentWorkingFileStartTime.containsKey(filePath)) return;

        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = this.currentWorkingFileStartTime.get(filePath);
        Duration duration = Duration.between(startTime, endTime);
        this.workedFileNameList.add(filePath);
        this.workedFileDuration.add(duration);

        this.currentWorkingFileStartTime.remove(filePath);
    }

    public String sessionInfo(){
        return "session start at " + this.getFormattedTime(this.sessionStartTime);
    }

    public void writeSessionStart(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.statFilePath, true))){
            String info = this.sessionInfo();
            writer.write(info);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public List<String> getAllStat(){
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

    public String getFileStat(String filePath){
        if(!this.currentWorkingFileStartTime.containsKey(filePath)){
            return null;
        }
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = this.currentWorkingFileStartTime.get(filePath);
        Duration duration = Duration.between(startTime, endTime);
        String result = filePath + " " + this.durationToString(duration);
        return result;
    }

    public String getFormattedTime(LocalDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
        String formattedDateTime = time.format(formatter);
        return formattedDateTime;
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
}

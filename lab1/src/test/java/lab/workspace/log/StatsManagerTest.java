package lab.workspace.log;

import lab.Config;
import lab.utils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class StatsManagerTest {

    @BeforeEach
    public void setup(){
        Utils.cleanFolder(Config.tempFolderPathRelative);
        Config.setup();
        Config.enableMemento = false;
    }

    @AfterEach
    public void cleanup(){
        Utils.cleanFolder(Config.tempFolderPathRelative);
    }

    @Test
    public void timeTest(){
        StatsManager statsManager = new StatsManager();
        LocalDateTime localDateTime = LocalDateTime.now();
        statsManager.initStatsManager();
        String filePath = "./test/hello.md";
        statsManager.fileWorkStart(filePath);
        try{
            Thread.sleep(5000);
        } catch (Exception e){
            e.printStackTrace();
            Assertions.assertTrue(false);
        }
        statsManager.fileWorkEnd(filePath);
        statsManager.writeSessionStats();

        List<String> lines = Utils.getFileLines(Config.statsFilePathRelative, "UTF-8");
        Assertions.assertTrue(lines.size() == 2);
        String sessionStartInfo = lines.get(0);
        String sessionPrefix = "session start at ";
        Assertions.assertTrue(sessionStartInfo.startsWith(sessionPrefix));
        String notedTimeStr = sessionStartInfo.substring(sessionPrefix.length());
        LocalDateTime notedTime = Utils.getLocalTime(notedTimeStr);
        Duration between = Duration.between(localDateTime, notedTime);
        long seconds = between.toSeconds();
        Assertions.assertTrue(seconds < 1);

        String fileInfo = lines.get(1);
        String filePrefix = "./test/hello.md 0分钟";
        String fileSuffix = "秒";
        Assertions.assertTrue(fileInfo.startsWith(filePrefix));
        Assertions.assertTrue(fileInfo.endsWith(fileSuffix));
        String minuteStr = fileInfo.substring(filePrefix.length(), fileInfo.length() - fileSuffix.length());
        long minutesNoted = Long.parseLong(minuteStr);
        Assertions.assertTrue(minutesNoted >= 4 && minutesNoted <= 6);
    }
}


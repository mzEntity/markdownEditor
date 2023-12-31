package lab.workspace;

import lab.cmd.cmd.Revocable;
import lab.memento.Retainable;
import lab.workspace.filetree.FileManager;
import lab.workspace.log.LogManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DeskTop implements Retainable {

    private CommandStack commandStack;
    private FileManager fileManager;
    private LogManager logManager;

    private boolean saved;

    public String filePath;

    public DeskTop(String filePath) {
        this.filePath = filePath;

        this.commandStack = new CommandStack();
        this.fileManager = new FileManager(filePath);
        this.logManager = new LogManager(filePath);

        this.saved = false;
    }

    public void initDeskTop(){
        this.fileManager.initFileManager();
    }

    public void addToHistory(Revocable cmd){
        this.commandStack.pushNewCommand(cmd);
    }

    public void clearHistory(){
        this.commandStack.clearStack();
    }

    public boolean saveFile(){
        boolean success = this.fileManager.saveFile();

        return success;
    }

    public void writeLogStats(){
        this.logManager.writeLog();
    }

    public void insertLine(long lineNumber, String prefix, String content){
        this.fileManager.insertLine(lineNumber, prefix, content);
    }

    public void insertLine(long lineNumber, String content){
        this.fileManager.insertLine(lineNumber, content);
    }

    public String removeLine(long lineNumber){
        return this.fileManager.removeLine(lineNumber);
    }

    public HashMap<Long, String> removeContent(String content){
        HashMap<Long, String> allMatches = this.findContent(content);
        if(allMatches == null){
            System.out.println("No such content.");
            return null;
        }
        List<Long> sortedKeys = new ArrayList<>(allMatches.keySet());
        Collections.sort(sortedKeys);
        int count = sortedKeys.size();
        for(int i = count - 1; i >= 0; i--){
            this.removeLine(sortedKeys.get(i));
        }
        return allMatches;
    }

    public HashMap<Long, String> findContent(String content){
        return this.fileManager.findContent(content);
    }

    public boolean listLines(){
        List<String> lines = this.fileManager.toFile();
        if(lines == null){
            return false;
        }
        for(String line: lines){
            System.out.println(line);
        }
        return true;
    }

    public boolean showTree(Long lineNumber){
        List<String> lines = this.fileManager.toTree(lineNumber);
        if(lines == null){
            return false;
        }
        if(lineNumber == 0){
            System.out.println("File Content: ");
        } else {
            System.out.println("Tree in line " + lineNumber + ": ");
        }
        for(String line: lines){
            System.out.println(line);
        }
        return true;
    }

    public boolean showTree(String content){
        HashMap<Long, String> allMatches = this.findContent(content);
        if(allMatches == null) return false;
        List<Long> sortedKeys = new ArrayList<>(allMatches.keySet());
        Collections.sort(sortedKeys);
        int count = sortedKeys.size();
        for(int i = 0; i < count; i++){
            long lineNumber = sortedKeys.get(i);
            List<String> lines = this.fileManager.toTree(lineNumber);
            System.out.println("Tree(" + content + ") in line " + lineNumber + ": ");
            for(String line: lines){
                System.out.println(line);
            }
        }
        return true;
    }

    public void showLog(int logCount){
        List<String> logs = this.logManager.getLog(logCount);
        int count = logs.size();
        for(int i = count-1; i >=0; i--){
            System.out.println(logs.get(i));
        }
    }



    public boolean undoCommand(){
        Revocable cmd = this.commandStack.undoCommand();
        if(cmd == null){
            System.out.println("Can't undo. No revocable operations.");
            return false;
        }
        cmd.undo();
        return true;
    }

    public boolean redoCommand(){
        Revocable cmd = this.commandStack.redoCommand();
        if(cmd == null) {
            System.out.println("Can't redo. No revocable operations.");
            return false;
        }
        cmd.redo();
        return true;
    }


    public void info(String msg){
        this.logManager.info(msg);
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}

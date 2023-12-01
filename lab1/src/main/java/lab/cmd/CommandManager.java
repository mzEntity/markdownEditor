package lab.cmd;

import lab.Config;
import lab.client.ConsoleManager;
import lab.cmd.cmd.concrete.global.ExitCommand;
import lab.cmd.cmd.concrete.local.SaveCommand;
import lab.memento.Retainable;
import lab.utils.Utils;
import lab.workspace.DeskTop;
import lab.cmd.cmd.*;
import lab.workspace.log.StatsManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager implements Retainable {
    private HashMap<String, DeskTop> allWorkSpaces;
    private DeskTop deskTop;

    private List<Boolean> hasEnd;
    private StatsManager statsManager;

    private String basePath;

    public CommandManager() {
        this.allWorkSpaces = new HashMap<>();
        this.statsManager = new StatsManager();
        this.deskTop = null;
        this.hasEnd = new ArrayList<>();
        this.basePath = Utils.getNormalizedAbsolutePath(".");
    }

    public void initCommandManager(){
        this.statsManager.initStatsManager();
        for(String key: this.allWorkSpaces.keySet()){
            this.allWorkSpaces.get(key).initDeskTop();
        }
    }

    public boolean dealWithRequest(List<String> request){
        Executable cmd = this.parseRequest(request);
        if(cmd == null){
            System.out.println("Invalid command");
            return true;
        }

        boolean executeSuccess = cmd.execute();

        if(!executeSuccess){
            return true;
        }

        if(cmd instanceof LocalScale){
            deskTop.setSaved(false);

            if(cmd instanceof Revocable){
                deskTop.addToHistory((Revocable) cmd);
            }
            if(cmd instanceof Unskippable){
                deskTop.clearHistory();
            }
            deskTop.info(cmd.description());

            if(cmd instanceof SaveCommand){
                deskTop.setSaved(true);
                deskTop.writeLogStats();

                String filePath = this.deskTop.filePath;
                this.statsManager.fileWorkEnd(filePath);
                this.statsManager.fileWorkStart(filePath);
            }
        }


        return !(cmd instanceof ExitCommand);
    }

    public void showAllStat(){
        List<String> stats = this.statsManager.getAllStat();
        for(String stat: stats){
            System.out.println(stat);
        }
    }

    public void showCurrentStat(){
        String currentStat = this.statsManager.getFileStat(this.deskTop.filePath);
        if(currentStat == null) return;
        System.out.println(currentStat);
    }

    public void load(String filePath){
        if(this.allWorkSpaces.containsKey(filePath)){
            System.out.println("Workspace has already been opened.");
            this.changeActiveWorkSpace(filePath);
            return;
        }
        DeskTop d = new DeskTop(filePath);
        d.initDeskTop();
        this.allWorkSpaces.put(filePath, d);
        this.changeActiveWorkSpace(filePath);
        this.statsManager.fileWorkStart(filePath);
    }

    public void open(String filePath){
        this.changeActiveWorkSpace(filePath);
    }

    public void close(){
        if(this.deskTop == null){
            System.out.println("No active workspace.");
            return;
        }

        String filePath = this.deskTop.filePath;
        this.saveSession(this.deskTop);

        this.deskTop = null;
        this.allWorkSpaces.remove(filePath);
    }

    public void exit(){
        Boolean allSaved = true;
        for(String key: this.allWorkSpaces.keySet()){
            DeskTop target = this.allWorkSpaces.get(key);
            boolean saved = this.saveSession(target);
            if(!saved) allSaved = false;
            else {
                this.statsManager.fileWorkEnd(key);
            }
        }
        this.statsManager.writeSessionStat();
        if(allSaved && Config.enableMemento){
            Utils.saveObject(Config.mementoFilePathRelative, this);
            System.out.println("All saved. Save all workspaces.");
        }
        this.allWorkSpaces.clear();
    }

    public void listWorkspace(){
        String prefix = "   ";
        String activePrefix = "-> ";
        for(String filePath: this.allWorkSpaces.keySet()){
            DeskTop target = this.allWorkSpaces.get(filePath);
            String suffix = " *";
            String relativeFilePath = Utils.getRelativePath(filePath, this.basePath);
            if(target == this.deskTop){
                System.out.print(activePrefix + relativeFilePath);
            } else {
                System.out.print(prefix + relativeFilePath);
            }
            if(!target.isSaved()){
                System.out.println(suffix);
            } else {
                System.out.println();
            }
        }
    }

    private boolean saveSession(DeskTop targetDeskTop){
        boolean saved = true;
        if(!targetDeskTop.isSaved()){
            String filePath = Utils.getRelativePath(targetDeskTop.filePath, basePath);
            if(askForSave(filePath)){
                targetDeskTop.saveFile();
                targetDeskTop.setSaved(true);
                targetDeskTop.writeLogStats();
            } else {
                saved = false;
            }
        }
        return saved;
    }

    private boolean askForSave(String filePath){
        System.out.printf("Do you want to save the workspace (%s) [Y/N] ?\n", filePath);
        String choice = null;
        do{
            String inputStr = ConsoleManager.getConsoleManager().getLine();
            if(inputStr.length() <= 0){
                choice = "0";
            } else {
                choice = inputStr.substring(0, 1);
            }
        } while(!"YNyn".contains(choice));
        return choice.equalsIgnoreCase("Y");
    }

    private boolean changeActiveWorkSpace(String filePath){
        if(this.allWorkSpaces.containsKey(filePath)){
            this.deskTop = this.allWorkSpaces.get(filePath);
            return true;
        }
        return false;
    }

    private Executable parseRequest(List<String> request){
        Executable cmd = CommandFactory.parseGlobalCommand(request, this);
        if(cmd == null){
            cmd = CommandFactory.parseLocalCommand(request, this.deskTop);
        }
        return cmd;
    }

    public void listDirectoryContents(){
        if(this.deskTop == null) {
            System.out.println("No active workspace.");
            return;
        }
        this.hasEnd = new ArrayList<>();
        String currentFilePath = this.deskTop.filePath;
        File currentFile = new File(currentFilePath);
        String parentFilePath = currentFile.getParent();
        File parentFile = new File(parentFilePath);
        this.listAllChildFile(parentFile);
    }
    private void listAllChildFile(File directory){
        if(!directory.exists() || !directory.isDirectory()){
            return;
        }
        int prefixCount = this.hasEnd.size();
        StringBuilder prefix = new StringBuilder();
        for(boolean ended: this.hasEnd){
            if(ended){
                prefix.append("    ");
            } else {
                prefix.append("│   ");
            }
        }
        File[] files = directory.listFiles();
        if (files != null) {
            int fileCount = files.length;
            for(int i = 0; i < fileCount; i++){
                File currentFile = files[i];
                boolean last = i == (fileCount - 1);
                String currentPrefix = last ? "└── ": "├── ";
                String normalizedPath = Utils.getNormalizedAbsolutePath(currentFile.getPath());
                String suffix = "";
                if(this.allWorkSpaces.containsKey(normalizedPath)){
                    suffix = " *";
                }
                System.out.println(prefix + currentPrefix + currentFile.getName() + suffix);
                this.hasEnd.add(last);
                this.listAllChildFile(currentFile);
                this.hasEnd.remove(prefixCount);
            }
        }
    }

    public static void main(String[] args) {

    }
}

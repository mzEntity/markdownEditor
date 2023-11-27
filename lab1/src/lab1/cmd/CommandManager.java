package lab1.cmd;

import lab1.cmd.cmd.concrete.global.ExitCommand;
import lab1.cmd.cmd.concrete.local.SaveCommand;
import lab1.cmd.parser.console.concrete.GlobalConsoleParser;
import lab1.cmd.parser.console.concrete.LocalConsoleParser;
import lab1.cmd.parser.console.concrete.global.*;
import lab1.cmd.parser.console.concrete.local.*;
import lab1.memento.Retainable;
import lab1.utils.Utils;
import lab1.workspace.DeskTop;
import lab1.cmd.cmd.*;
import lab1.workspace.log.StatsManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CommandManager implements Retainable {
    private HashMap<String, DeskTop> allWorkSpaces;
    private DeskTop deskTop;

    private HashMap<String, LocalConsoleParser> allLocalCommands;
    private HashMap<String, GlobalConsoleParser> allGlobalCommands;

    private List<Boolean> hasEnd;
    private StatsManager statsManager;

    public CommandManager() {
        this.allWorkSpaces = new HashMap<>();
        this.allLocalCommands = new HashMap<>();
        this.allGlobalCommands = new HashMap<>();

        this.statsManager = new StatsManager();
        this.deskTop = null;
        this.hasEnd = new ArrayList<>();
        this.initCommandMapping();
    }

    public void initCommandManager(){
        this.statsManager.initStatsManager();
    }

    private void initCommandMapping(){
        this.allGlobalCommands.put("load", new LoadCommandConsoleParser());
        this.allGlobalCommands.put("open", new OpenCommandConsoleParser());
        this.allGlobalCommands.put("exit", new ExitCommandConsoleParser());
        this.allGlobalCommands.put("close", new CloseCommandConsoleParser());
        this.allGlobalCommands.put("list-workspace", new ListWorkspaceCommandConsoleParser());
        this.allGlobalCommands.put("ls", new LsCommandConsoleParser());
        this.allGlobalCommands.put("stats", new StatsCommandConsoleParser());

        this.allLocalCommands.put("save", new SaveCommandConsoleParser());
        this.allLocalCommands.put("insert", new InsertCommandConsoleParser());
        this.allLocalCommands.put("append-head", new AppendHeadCommandConsoleParser());
        this.allLocalCommands.put("append-tail", new AppendTailCommandConsoleParser());
        this.allLocalCommands.put("delete", new DeleteCommandConsoleParser());
        this.allLocalCommands.put("undo", new UndoCommandConsoleParser());
        this.allLocalCommands.put("redo", new RedoCommandConsoleParser());
        this.allLocalCommands.put("list", new ListCommandConsoleParser());
        this.allLocalCommands.put("dir-tree", new DirTreeCommandConsoleParser());
        this.allLocalCommands.put("list-tree", new ListTreeCommandConsoleParser());
        this.allLocalCommands.put("history", new HistoryCommandConsoleParser());
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
            return;
        }
        DeskTop d = new DeskTop(filePath);
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
        this.deskTop = null;
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
        if(allSaved){
            Utils.saveObject("./memento", this);
            System.out.println("All saved. Save all workspaces.");
        }
        this.allWorkSpaces.clear();
    }

    public void listWorkspace(){
        String prefix = "  ";
        String activePrefix = "->";
        for(String filePath: this.allWorkSpaces.keySet()){
            if(this.allWorkSpaces.get(filePath) == this.deskTop){
                System.out.println(activePrefix + filePath);
            } else {
                System.out.println(prefix + filePath);
            }
        }
    }

    private boolean saveSession(DeskTop targetDeskTop){
        boolean saved = true;
        if(!targetDeskTop.isSaved()){
            if(askForSave()){
                targetDeskTop.saveFile();
                targetDeskTop.setSaved(true);
                targetDeskTop.writeLogStats();
            } else {
                saved = false;
            }
        }
        return saved;
    }

    private boolean askForSave(){
        System.out.println("You haven't saved the file yet. Do you want to save it?[Y/N]");
        Scanner scanner = new Scanner(System.in);
        String choice = null;
        do{
            String inputStr = scanner.nextLine();
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
        String cmdType = request.get(0);
        if(this.allGlobalCommands.containsKey(cmdType)) {
            return this.allGlobalCommands.get(cmdType).createInstance(this, request);
        }
        else if(this.allLocalCommands.containsKey(cmdType)){
            if(this.deskTop == null){
                System.out.println("No active workspace.");
                return null;
            }
            return this.allLocalCommands.get(cmdType).createInstance(this.deskTop, request);
        } else {
            System.out.println("No such command");
            return null;
        }
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

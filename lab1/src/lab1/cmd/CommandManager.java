package lab1.cmd;

import lab1.cmd.cmd.concrete.SaveCommand;
import lab1.cmd.parser.console.concrete.*;
import lab1.workspace.DeskTop;
import lab1.cmd.cmd.*;
import lab1.cmd.parser.console.*;

import java.util.HashMap;
import java.util.List;

public class CommandManager {
    private HashMap<String, DeskTop> allWorkSpaces;
    private DeskTop deskTop;

    private HashMap<String, ConsoleParser> allCommands;

    public CommandManager() {
        this.allWorkSpaces = new HashMap<>();
        this.allCommands = new HashMap<>();
        this.deskTop = null;
        this.initCommandMapping();
    }

    private void initCommandMapping(){
//        this.allCommands.put("load", new LoadCommandConsoleParser());
//        this.allCommands.put("save", new SaveCommandConsoleParser());
        this.allCommands.put("insert", new InsertCommandConsoleParser());
        this.allCommands.put("append-head", new AppendHeadCommandConsoleParser());
        this.allCommands.put("append-tail", new AppendTailCommandConsoleParser());
        this.allCommands.put("delete", new DeleteCommandConsoleParser());
        this.allCommands.put("undo", new UndoCommandConsoleParser());
        this.allCommands.put("redo", new RedoCommandConsoleParser());
        this.allCommands.put("list", new ListCommandConsoleParser());
        this.allCommands.put("dir-tree", new DirTreeCommandConsoleParser());
        this.allCommands.put("list-tree", new ListTreeCommandConsoleParser());
        this.allCommands.put("history", new HistoryCommandConsoleParser());
        this.allCommands.put("stats", new StatsCommandConsoleParser());
    }

    private int dealWithRequestIfGlobal(List<String> request){
        String requestType = request.get(0);
        switch (requestType){
            case "load":
                this.load(request.get(1));
                break;
            case "save":
                this.save();
                break;
            case "open":
                this.open(request.get(1));
                break;
            case "close":
                this.close();
                break;
            case "exit":
                this.exit();
                return -1;
            default:
                return 0;
        }
        return 1;
    }

    public boolean dealWithRequest(List<String> request){
        int code = this.dealWithRequestIfGlobal(request);
        if(code == 1){
            return true;
        } else if(code == -1){
            return false;
        }
        Executable cmd = this.parseRequest(request);
        if(cmd == null){
            System.out.println("Invalid command");
        }
        else{
            if(cmd instanceof Revocable){
                deskTop.addToHistory((Revocable) cmd);
            }
            if(cmd instanceof Unskippable){
                deskTop.clearHistory();
            }
            boolean executeSuccess = cmd.execute();
            if(executeSuccess){
                deskTop.info(cmd.description());
            }
        }
        return true;
    }

    public void load(String filePath){
        this.allWorkSpaces.put(filePath, new DeskTop(filePath));
        this.changeActiveWorkSpace(filePath);
    }

    public void save(){
        this.deskTop.saveFile();
        this.deskTop.writeLogStats();
    }

    public void open(String filePath){
        this.changeActiveWorkSpace(filePath);
    }

    public void close(){
        String filePath = this.deskTop.filePath;
        this.deskTop = null;
        this.allWorkSpaces.remove(filePath);
    }

    public void exit(){
        this.deskTop = null;
        for(String key: this.allWorkSpaces.keySet()){
            this.allWorkSpaces.remove(key);
        }
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
        if(!this.allCommands.containsKey(cmdType)) {
            System.out.println("No such command");
            return null;
        }
        else {
            return this.allCommands.get(cmdType).createInstance(this.deskTop, request);
        }
    }

}

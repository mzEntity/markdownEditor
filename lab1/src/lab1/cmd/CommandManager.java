package lab1.cmd;

import lab1.cmd.cmd.concrete.global.ExitCommand;
import lab1.cmd.cmd.concrete.local.SaveCommand;
import lab1.cmd.parser.console.concrete.GlobalConsoleParser;
import lab1.cmd.parser.console.concrete.LocalConsoleParser;
import lab1.cmd.parser.console.concrete.global.CloseCommandConsoleParser;
import lab1.cmd.parser.console.concrete.global.ExitCommandConsoleParser;
import lab1.cmd.parser.console.concrete.global.LoadCommandConsoleParser;
import lab1.cmd.parser.console.concrete.global.OpenCommandConsoleParser;
import lab1.cmd.parser.console.concrete.local.*;
import lab1.workspace.DeskTop;
import lab1.cmd.cmd.*;
import lab1.cmd.parser.console.*;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CommandManager {
    private HashMap<String, DeskTop> allWorkSpaces;
    private DeskTop deskTop;

    private HashMap<String, LocalConsoleParser> allLocalCommands;
    private HashMap<String, GlobalConsoleParser> allGlobalCommands;

    public CommandManager() {
        this.allWorkSpaces = new HashMap<>();
        this.allLocalCommands = new HashMap<>();
        this.allGlobalCommands = new HashMap<>();
        
        this.deskTop = null;
        this.initCommandMapping();
    }

    private void initCommandMapping(){
        this.allGlobalCommands.put("load", new LoadCommandConsoleParser());
        this.allGlobalCommands.put("open", new OpenCommandConsoleParser());
        this.allGlobalCommands.put("exit", new ExitCommandConsoleParser());
        this.allGlobalCommands.put("close", new CloseCommandConsoleParser());

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
        this.allLocalCommands.put("stats", new StatsCommandConsoleParser());
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
            }
        }


        return !(cmd instanceof ExitCommand);
    }

    public void load(String filePath){
        if(this.allWorkSpaces.containsKey(filePath)){
            System.out.println("Workspace has already been opened.");
            return;
        }
        this.allWorkSpaces.put(filePath, new DeskTop(filePath));
        this.changeActiveWorkSpace(filePath);
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
        for(String key: this.allWorkSpaces.keySet()){
            DeskTop target = this.allWorkSpaces.get(key);
            this.saveSession(target);
        }
        this.allWorkSpaces.clear();
    }

    private void saveSession(DeskTop targetDeskTop){
        if(!targetDeskTop.isSaved()){
            if(askForSave()){
                targetDeskTop.saveFile();
                targetDeskTop.setSaved(true);
                targetDeskTop.writeLogStats();
            }
        }
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

}

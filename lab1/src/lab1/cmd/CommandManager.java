package lab1.cmd;

import lab1.cmd.cmd.concrete.SecretaryDecorator;
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

    public void initCommandManager(){
        this.deskTop = new DeskTop();
    }

    private void initCommandMapping(){
        this.allCommands.put("load", new LoadCommandConsoleParser());
        this.allCommands.put("save", new SaveCommandConsoleParser());
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

    public void dealWithRequest(List<String> request){
        Executable cmd = this.parseRequest(request);
        if(cmd == null){
            System.out.println("Invalid command");
        }
        else{
            this.executeCommand(cmd);
        }
    }

    public void dealWithQuit(){
        this.deskTop.cleanUp();
    }

    private Executable parseRequest(List<String> request){
        String cmdType = request.get(0);
        if(!this.allCommands.containsKey(cmdType)) {
            System.out.println("No such command");
            return null;
        }
        else {
            Executable cmd = this.allCommands.get(cmdType).createInstance(this.deskTop, request);
            if(cmd == null) return null;
            else return new SecretaryDecorator(this.deskTop, cmd);
        }
    }

    public void executeCommand(Executable c){
        c.execute();
    }
}

package lab.cmd;

import lab.cmd.cmd.GlobalScale;
import lab.cmd.cmd.LocalScale;
import lab.cmd.parser.console.concrete.GlobalConsoleParser;
import lab.cmd.parser.console.concrete.LocalConsoleParser;
import lab.cmd.parser.console.concrete.global.*;
import lab.cmd.parser.console.concrete.local.*;
import lab.workspace.DeskTop;

import java.util.HashMap;
import java.util.List;

public class CommandFactory {
    private static HashMap<String, LocalConsoleParser> allLocalCommands;
    private static HashMap<String, GlobalConsoleParser> allGlobalCommands;

    static {
        initCommandMapping();
    }

    private static void initCommandMapping(){
        allGlobalCommands = new HashMap<>();
        allLocalCommands = new HashMap<>();

        allGlobalCommands.put("load", new LoadCommandConsoleParser());
        allGlobalCommands.put("open", new OpenCommandConsoleParser());
        allGlobalCommands.put("exit", new ExitCommandConsoleParser());
        allGlobalCommands.put("close", new CloseCommandConsoleParser());
        allGlobalCommands.put("list-workspace", new ListWorkspaceCommandConsoleParser());
        allGlobalCommands.put("ls", new LsCommandConsoleParser());
        allGlobalCommands.put("stats", new StatsCommandConsoleParser());

        allLocalCommands.put("save", new SaveCommandConsoleParser());
        allLocalCommands.put("insert", new InsertCommandConsoleParser());
        allLocalCommands.put("append-head", new AppendHeadCommandConsoleParser());
        allLocalCommands.put("append-tail", new AppendTailCommandConsoleParser());
        allLocalCommands.put("delete", new DeleteCommandConsoleParser());
        allLocalCommands.put("undo", new UndoCommandConsoleParser());
        allLocalCommands.put("redo", new RedoCommandConsoleParser());
        allLocalCommands.put("list", new ListCommandConsoleParser());
        allLocalCommands.put("dir-tree", new DirTreeCommandConsoleParser());
        allLocalCommands.put("list-tree", new ListTreeCommandConsoleParser());
        allLocalCommands.put("history", new HistoryCommandConsoleParser());
    }

    public static GlobalScale parseGlobalCommand(List<String> request, CommandManager receiver){
        String cmdType = request.get(0);
        if(allGlobalCommands.containsKey(cmdType)) {
            return allGlobalCommands.get(cmdType).createInstance(receiver, request);
        } else {
            return null;
        }
    }

    public static LocalScale parseLocalCommand(List<String> request, DeskTop receiver){
        String cmdType = request.get(0);
        if(allLocalCommands.containsKey(cmdType)){
            if(receiver == null){
                System.out.println("No active workspace.");
                return null;
            }
            return allLocalCommands.get(cmdType).createInstance(receiver, request);
        } else {
            return null;
        }
    }
}

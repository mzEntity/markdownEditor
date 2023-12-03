package lab.cmd;

import lab.client.InputManager;
import lab.utils.Utils;
import lab.workspace.DeskTop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CommandFactoryTest {
    private static InputManager inputManager;
    private static CommandManager commandManager;
    private static DeskTop deskTop;

    @BeforeAll
    public static void setup(){
        inputManager = new InputManager();
        commandManager = new CommandManager();
        deskTop = new DeskTop(Utils.getNormalizedAbsolutePath("./demo.txt"));
    }

    @Test
    public void globalTest(){
        String[] jobs = {
                "close",
                "exit",
                "list-workspace",
                "load hello.md",
                "ls",
                "open main.md",
                "stats all"
        };
        String[] classes = {
                "lab.cmd.cmd.concrete.global.CloseCommand",
                "lab.cmd.cmd.concrete.global.ExitCommand",
                "lab.cmd.cmd.concrete.global.ListWorkspaceCommand",
                "lab.cmd.cmd.concrete.global.LoadCommand",
                "lab.cmd.cmd.concrete.global.LsCommand",
                "lab.cmd.cmd.concrete.global.OpenCommand",
                "lab.cmd.cmd.concrete.global.StatsCommand"
        };
        int length = jobs.length;
        for(int i = 0; i < length; i++){
            List<String> params = inputManager.splitInputStr(jobs[i]);
            try {
                Class<?> clazz = Class.forName(classes[i]);
                Assertions.assertTrue(clazz.isInstance(CommandFactory.parseGlobalCommand(params, commandManager)));
            } catch (ClassNotFoundException e) {
                Assertions.assertTrue(false);
            }
        }
    }

    @Test
    public void globalInvalidTest(){
        String[] jobs = {
                "close current",
                "exit all",
                "list-workspaces",
                "load",
                "ls -h",
                "open -f hello.md",
                "invalidCMD"
        };
        int length = jobs.length;
        for(int i = 0; i < length; i++){
            List<String> params = inputManager.splitInputStr(jobs[i]);
            Assertions.assertNull(CommandFactory.parseGlobalCommand(params, commandManager));
        }
    }

    @Test
    public void localTest(){
        String[] jobs = {
                "append-head hello",
                "append-tail 30",
                "delete 3",
                "dir-tree",
                "history 3",
                "insert ## \"title 2\"",
                "list",
                "list-tree",
                "redo",
                "undo",
                "save"
        };
        String[] classes = {
                "lab.cmd.cmd.concrete.local.AppendHeadCommand",
                "lab.cmd.cmd.concrete.local.AppendTailCommand",
                "lab.cmd.cmd.concrete.local.DeleteCommand",
                "lab.cmd.cmd.concrete.local.DirTreeCommand",
                "lab.cmd.cmd.concrete.local.HistoryCommand",
                "lab.cmd.cmd.concrete.local.InsertCommand",
                "lab.cmd.cmd.concrete.local.ListCommand",
                "lab.cmd.cmd.concrete.local.ListTreeCommand",
                "lab.cmd.cmd.concrete.local.RedoCommand",
                "lab.cmd.cmd.concrete.local.UndoCommand",
                "lab.cmd.cmd.concrete.local.SaveCommand",

        };
        int length = jobs.length;
        for(int i = 0; i < length; i++){
            List<String> params = inputManager.splitInputStr(jobs[i]);
            try {
                Class<?> clazz = Class.forName(classes[i]);
                Assertions.assertTrue(clazz.isInstance(CommandFactory.parseLocalCommand(params, deskTop)));
            } catch (ClassNotFoundException e) {
                Assertions.assertTrue(false);
            }
        }
    }

    @Test
    public void localInvalidTest(){
        String[] jobs = {
                "append-head",
                "delete 3 hello",
                "dir-tree root",
                "history current",
                "insert ## title 2",
                "list all",
                "tree-list",
                "save hello.md",
                "stats al",
                "invalidCMD"
        };
        int length = jobs.length;
        for(int i = 0; i < length; i++){
            List<String> params = inputManager.splitInputStr(jobs[i]);
            Assertions.assertNull(CommandFactory.parseGlobalCommand(params, commandManager));
        }
    }

    @Test
    public void localNullTest(){
        String[] jobs = {
                "append-head hello",
                "append-tail 30",
                "delete 3",
                "dir-tree",
                "history 3",
                "insert ## \"title 2\"",
                "list",
                "list-tree",
                "redo",
                "undo",
                "save"
        };
        int length = jobs.length;
        for(int i = 0; i < length; i++){
            List<String> params = inputManager.splitInputStr(jobs[i]);
            Assertions.assertNull(CommandFactory.parseGlobalCommand(params, null));
        }
    }

}

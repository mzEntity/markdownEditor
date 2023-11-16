package lab1.cmd.parser.console.concrete;

import lab1.cmd.parser.console.ConsoleParser;
import lab1.workspace.DeskTop;
import lab1.cmd.cmd.Executable;
import lab1.cmd.cmd.concrete.UndoCommand;

import java.util.List;

public class UndoCommandConsoleParser implements ConsoleParser {
    @Override
    public Executable createInstance(DeskTop receiver, List<String> info) {
        if (info.size() != 1 || !info.get(0).equals("undo")) {
            return null;
        }
        return new UndoCommand(receiver);
    }
}

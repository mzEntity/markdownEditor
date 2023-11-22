package lab1.cmd.parser.console.concrete.local;

import lab1.cmd.cmd.LocalScale;
import lab1.cmd.parser.console.concrete.LocalConsoleParser;
import lab1.workspace.DeskTop;
import lab1.cmd.cmd.Executable;
import lab1.cmd.cmd.concrete.local.RedoCommand;

import java.util.List;

public class RedoCommandConsoleParser implements LocalConsoleParser {
    @Override
    public LocalScale createInstance(DeskTop receiver, List<String> info) {
        if (info.size() != 1 || !info.get(0).equals("redo")) {
            return null;
        }
        return new RedoCommand(receiver);
    }
}

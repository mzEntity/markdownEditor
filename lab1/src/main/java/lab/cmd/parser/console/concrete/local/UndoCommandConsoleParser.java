package lab.cmd.parser.console.concrete.local;

import lab.cmd.cmd.LocalScale;
import lab.cmd.parser.console.concrete.LocalConsoleParser;
import lab.workspace.DeskTop;
import lab.cmd.cmd.concrete.local.UndoCommand;

import java.util.List;

public class UndoCommandConsoleParser implements LocalConsoleParser {
    @Override
    public LocalScale createInstance(DeskTop receiver, List<String> info) {
        if (info.size() != 1 || !info.get(0).equals("undo")) {
            return null;
        }
        return new UndoCommand(receiver);
    }
}

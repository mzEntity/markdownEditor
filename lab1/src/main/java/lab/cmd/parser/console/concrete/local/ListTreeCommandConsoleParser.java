package lab.cmd.parser.console.concrete.local;

import lab.cmd.cmd.LocalScale;
import lab.cmd.parser.console.concrete.LocalConsoleParser;
import lab.workspace.DeskTop;
import lab.cmd.cmd.concrete.local.ListTreeCommand;

import java.util.List;

public class ListTreeCommandConsoleParser implements LocalConsoleParser {
    @Override
    public LocalScale createInstance(DeskTop receiver, List<String> info) {
        if (info.size() != 1 || !info.get(0).equals("list-tree")) {
            return null;
        }
        return new ListTreeCommand(receiver);
    }
}

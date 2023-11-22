package lab1.cmd.parser.console.concrete.global;

import lab1.cmd.CommandManager;
import lab1.cmd.cmd.GlobalScale;
import lab1.cmd.parser.console.concrete.GlobalConsoleParser;
import lab1.cmd.parser.console.concrete.LocalConsoleParser;
import lab1.workspace.DeskTop;
import lab1.cmd.cmd.Executable;
import lab1.cmd.cmd.concrete.global.LoadCommand;

import java.util.List;

public class LoadCommandConsoleParser implements GlobalConsoleParser {
    @Override
    public GlobalScale createInstance(CommandManager receiver, List<String> info) {
        if (info.size() != 2 || !info.get(0).equals("load")) {
            return null;
        }
        return new LoadCommand(receiver, info.get(1));
    }
}

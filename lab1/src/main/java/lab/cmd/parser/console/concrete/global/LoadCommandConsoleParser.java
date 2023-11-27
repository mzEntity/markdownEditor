package lab.cmd.parser.console.concrete.global;

import lab.cmd.CommandManager;
import lab.cmd.cmd.GlobalScale;
import lab.cmd.parser.console.concrete.GlobalConsoleParser;
import lab.cmd.cmd.concrete.global.LoadCommand;

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

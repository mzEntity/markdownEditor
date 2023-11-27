package lab.cmd.parser.console.concrete.global;

import lab.cmd.CommandManager;
import lab.cmd.cmd.GlobalScale;
import lab.cmd.cmd.concrete.global.OpenCommand;
import lab.cmd.parser.console.concrete.GlobalConsoleParser;

import java.util.List;

public class OpenCommandConsoleParser implements GlobalConsoleParser {
    @Override
    public GlobalScale createInstance(CommandManager receiver, List<String> info) {
        if (info.size() != 2 || !info.get(0).equals("open")) {
            return null;
        }
        return new OpenCommand(receiver, info.get(1));
    }
}

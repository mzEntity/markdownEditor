package lab1.cmd.parser.console.concrete.global;

import lab1.cmd.CommandManager;
import lab1.cmd.cmd.GlobalScale;
import lab1.cmd.cmd.concrete.global.LsCommand;
import lab1.cmd.parser.console.concrete.GlobalConsoleParser;

import java.util.List;

public class LsCommandConsoleParser implements GlobalConsoleParser {
    @Override
    public GlobalScale createInstance(CommandManager receiver, List<String> info) {
        if (info.size() != 1 || !info.get(0).equals("ls")) {
            return null;
        }
        return new LsCommand(receiver);
    }
}

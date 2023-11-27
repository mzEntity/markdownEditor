package lab.cmd.parser.console.concrete.global;

import lab.cmd.CommandManager;
import lab.cmd.cmd.GlobalScale;
import lab.cmd.cmd.concrete.global.LsCommand;
import lab.cmd.parser.console.concrete.GlobalConsoleParser;

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

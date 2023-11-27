package lab.cmd.parser.console.concrete.global;

import lab.cmd.CommandManager;
import lab.cmd.cmd.GlobalScale;
import lab.cmd.cmd.concrete.global.ExitCommand;
import lab.cmd.parser.console.concrete.GlobalConsoleParser;

import java.util.List;

public class ExitCommandConsoleParser implements GlobalConsoleParser {
    @Override
    public GlobalScale createInstance(CommandManager receiver, List<String> info) {
        if (info.size() != 1 || !info.get(0).equals("exit")) {
            return null;
        }
        return new ExitCommand(receiver);
    }
}

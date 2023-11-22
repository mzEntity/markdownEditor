package lab1.cmd.parser.console.concrete.global;

import lab1.cmd.CommandManager;
import lab1.cmd.cmd.GlobalScale;
import lab1.cmd.cmd.concrete.global.ExitCommand;
import lab1.cmd.cmd.concrete.global.OpenCommand;
import lab1.cmd.parser.console.concrete.GlobalConsoleParser;

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

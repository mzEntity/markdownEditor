package lab.cmd.parser.console.concrete.global;

import lab.cmd.CommandManager;
import lab.cmd.cmd.GlobalScale;
import lab.cmd.cmd.concrete.global.CloseCommand;
import lab.cmd.parser.console.concrete.GlobalConsoleParser;

import java.util.List;

public class CloseCommandConsoleParser implements GlobalConsoleParser {
    @Override
    public GlobalScale createInstance(CommandManager receiver, List<String> info) {
        if (info.size() != 1 || !info.get(0).equals("close")) {
            return null;
        }
        return new CloseCommand(receiver);
    }
}

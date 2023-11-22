package lab1.cmd.parser.console.concrete.global;

import lab1.cmd.CommandManager;
import lab1.cmd.cmd.GlobalScale;
import lab1.cmd.cmd.LocalScale;
import lab1.cmd.cmd.concrete.global.CloseCommand;
import lab1.cmd.cmd.concrete.global.ExitCommand;
import lab1.cmd.cmd.concrete.local.SaveCommand;
import lab1.cmd.parser.console.concrete.GlobalConsoleParser;
import lab1.cmd.parser.console.concrete.LocalConsoleParser;
import lab1.workspace.DeskTop;

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

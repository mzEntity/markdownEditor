package lab1.cmd.parser.console.concrete;

import lab1.cmd.parser.console.ConsoleParser;
import lab1.workspace.DeskTop;
import lab1.cmd.cmd.Executable;
import lab1.cmd.cmd.concrete.SaveCommand;

import java.util.List;

public class SaveCommandConsoleParser implements ConsoleParser {
    @Override
    public Executable createInstance(DeskTop receiver, List<String> info) {
        if (info.size() != 1 || !info.get(0).equals("save")) return null;
        return new SaveCommand(receiver);
    }
}

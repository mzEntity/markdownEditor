package lab1.cmd.parser.console.concrete;

import lab1.cmd.parser.console.ConsoleParser;
import lab1.workspace.DeskTop;
import lab1.cmd.cmd.Executable;
import lab1.cmd.cmd.concrete.LoadCommand;

import java.util.List;

public class LoadCommandConsoleParser implements ConsoleParser {
    @Override
    public Executable createInstance(DeskTop receiver, List<String> info) {
        if (info.size() != 2 || !info.get(0).equals("load")) {
            return null;
        }
        return new LoadCommand(receiver, info.get(1));
    }
}

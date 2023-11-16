package lab1.cmd.parser.console.concrete;

import lab1.cmd.parser.console.ConsoleParser;
import lab1.workspace.DeskTop;
import lab1.cmd.cmd.concrete.AppendHeadCommand;
import lab1.cmd.cmd.Executable;

import java.util.List;

public class AppendHeadCommandConsoleParser implements ConsoleParser {
    @Override
    public Executable createInstance(DeskTop receiver, List<String> info) {
        if ((info.size() != 2 && info.size() != 3) || !info.get(0).equals("append-head")) {
            return null;
        }
        String prefix, content;
        if (info.size() == 2) {
            prefix = null;
            content = info.get(1);
        } else {
            prefix = info.get(1);
            content = info.get(2);
        }
        return new AppendHeadCommand(receiver, prefix, content);
    }
}

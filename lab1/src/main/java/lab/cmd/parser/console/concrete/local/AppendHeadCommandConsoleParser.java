package lab.cmd.parser.console.concrete.local;

import lab.cmd.cmd.LocalScale;
import lab.cmd.parser.console.concrete.LocalConsoleParser;
import lab.workspace.DeskTop;
import lab.cmd.cmd.concrete.local.AppendHeadCommand;

import java.util.List;

public class AppendHeadCommandConsoleParser implements LocalConsoleParser {
    @Override
    public LocalScale createInstance(DeskTop receiver, List<String> info) {
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

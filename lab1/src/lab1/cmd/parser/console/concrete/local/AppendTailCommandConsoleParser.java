package lab1.cmd.parser.console.concrete.local;

import lab1.cmd.cmd.LocalScale;
import lab1.cmd.parser.console.concrete.LocalConsoleParser;
import lab1.workspace.DeskTop;
import lab1.cmd.cmd.concrete.local.AppendTailCommand;
import lab1.cmd.cmd.Executable;

import java.util.List;

public class AppendTailCommandConsoleParser implements LocalConsoleParser {

    @Override
    public LocalScale createInstance(DeskTop receiver, List<String> info) {
        if ((info.size() != 2 && info.size() != 3) || !info.get(0).equals("append-tail")) {
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
        return new AppendTailCommand(receiver, prefix, content);
    }
}

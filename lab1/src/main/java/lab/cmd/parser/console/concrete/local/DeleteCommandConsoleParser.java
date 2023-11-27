package lab.cmd.parser.console.concrete.local;

import lab.cmd.cmd.LocalScale;
import lab.cmd.parser.console.concrete.LocalConsoleParser;
import lab.workspace.DeskTop;
import lab.cmd.cmd.concrete.local.DeleteCommand;

import java.util.List;
import java.util.regex.Pattern;

public class DeleteCommandConsoleParser implements LocalConsoleParser {
    @Override
    public LocalScale createInstance(DeskTop receiver, List<String> info) {
        if (info.size() != 2 || !info.get(0).equals("delete")) {
            return null;
        }
        String para = info.get(1);
        if (Pattern.matches("\\d+", para)) {
            return new DeleteCommand(receiver, Long.parseLong(info.get(1)));
        } else {
            return new DeleteCommand(receiver, para);
        }
    }
}

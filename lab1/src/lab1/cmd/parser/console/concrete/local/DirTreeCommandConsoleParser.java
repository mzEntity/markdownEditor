package lab1.cmd.parser.console.concrete.local;

import lab1.cmd.cmd.LocalScale;
import lab1.cmd.parser.console.concrete.LocalConsoleParser;
import lab1.workspace.DeskTop;
import lab1.cmd.cmd.concrete.local.DirTreeCommand;
import lab1.cmd.cmd.Executable;

import java.util.List;
import java.util.regex.Pattern;

public class DirTreeCommandConsoleParser implements LocalConsoleParser {
    @Override
    public LocalScale createInstance(DeskTop receiver, List<String> info) {
        if ((info.size() != 1 && info.size() != 2) || !info.get(0).equals("dir-tree")) {
            return null;
        }
        if (info.size() == 2) {
            String para = info.get(1);
            if (Pattern.matches("\\d+", para)) {
                return new DirTreeCommand(receiver, Long.parseLong(para));
            } else {
                return new DirTreeCommand(receiver, para);
            }
        } else {
            return new DirTreeCommand(receiver, 0);
        }
    }
}

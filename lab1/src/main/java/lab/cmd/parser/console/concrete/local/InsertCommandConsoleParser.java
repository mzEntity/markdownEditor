package lab.cmd.parser.console.concrete.local;

import lab.cmd.cmd.LocalScale;
import lab.cmd.parser.console.concrete.LocalConsoleParser;
import lab.workspace.DeskTop;
import lab.cmd.cmd.concrete.local.InsertCommand;
import lab.utils.DataTypeChecker;

import java.util.List;

public class InsertCommandConsoleParser implements LocalConsoleParser {
    @Override
    public LocalScale createInstance(DeskTop receiver, List<String> info) {
        if ((info.size() != 2 && info.size() != 3 && info.size() != 4) || !info.get(0).equals("insert")) {
            return null;
        }
        long lineNumber;
        String prefix, content;
        if (info.size() == 4) {
            String para = info.get(1);
            if (DataTypeChecker.isNumber(para)) {
                lineNumber = Long.parseLong(info.get(1));
            } else {
                return null;
            }
            prefix = info.get(2);
            content = info.get(3);
        } else if (info.size() == 3) {
            String para = info.get(1);
            if (DataTypeChecker.isNumber(para)) {
                lineNumber = Long.parseLong(para);
                prefix = null;
                content = info.get(2);
            } else {
                lineNumber = -1;
                prefix = info.get(1);
                content = info.get(2);
            }
        } else {
            lineNumber = -1;
            prefix = null;
            content = info.get(1);
        }

        return new InsertCommand(receiver, lineNumber, prefix, content);
    }
}

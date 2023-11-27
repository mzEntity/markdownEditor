package lab.cmd.parser.console.concrete.local;

import lab.cmd.cmd.LocalScale;
import lab.cmd.parser.console.concrete.LocalConsoleParser;
import lab.workspace.DeskTop;
import lab.cmd.cmd.concrete.local.HistoryCommand;
import lab.utils.DataTypeChecker;

import java.util.List;

public class HistoryCommandConsoleParser implements LocalConsoleParser {

    @Override
    public LocalScale createInstance(DeskTop receiver, List<String> info) {
        if ((info.size() != 2 && info.size() != 1) || !info.get(0).equals("history")) {
            return null;
        }
        int historyLength = -1;
        if (info.size() == 2) {
            if (DataTypeChecker.isNumber(info.get(1))) {
                historyLength = Integer.parseInt(info.get(1));
            } else {
                return null;
            }
        }
        return new HistoryCommand(receiver, historyLength);
    }
}

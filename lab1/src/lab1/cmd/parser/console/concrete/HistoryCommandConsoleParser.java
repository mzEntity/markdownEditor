package lab1.cmd.parser.console.concrete;

import lab1.cmd.parser.console.ConsoleParser;
import lab1.workspace.DeskTop;
import lab1.cmd.cmd.Executable;
import lab1.cmd.cmd.concrete.HistoryCommand;
import lab1.utils.DataTypeChecker;

import java.util.List;

public class HistoryCommandConsoleParser implements ConsoleParser {

    @Override
    public Executable createInstance(DeskTop receiver, List<String> info) {
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

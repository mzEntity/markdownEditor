package lab1.cmd.parser.console.concrete;

import lab1.cmd.parser.console.ConsoleParser;
import lab1.workspace.DeskTop;
import lab1.cmd.cmd.Executable;
import lab1.cmd.cmd.concrete.StatsCommand;

import java.util.List;

public class StatsCommandConsoleParser implements ConsoleParser {
    @Override
    public Executable createInstance(DeskTop receiver, List<String> info) {
        if (info.size() != 2 || !info.get(0).equals("stats")) {
            return null;
        }
        return switch (info.get(1)) {
            case "all" -> new StatsCommand(receiver, StatsCommand.STATS_ALL);
            case "current" -> new StatsCommand(receiver, StatsCommand.STATS_CURRENT);
            default -> null;
        };
    }
}

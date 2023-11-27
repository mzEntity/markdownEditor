package lab1.cmd.parser.console.concrete.local;

import lab1.cmd.CommandManager;
import lab1.cmd.cmd.GlobalScale;
import lab1.cmd.cmd.LocalScale;
import lab1.cmd.parser.console.concrete.GlobalConsoleParser;
import lab1.cmd.parser.console.concrete.LocalConsoleParser;
import lab1.workspace.DeskTop;
import lab1.cmd.cmd.Executable;
import lab1.cmd.cmd.concrete.local.StatsCommand;

import java.util.List;

public class StatsCommandConsoleParser implements GlobalConsoleParser {
    @Override
    public GlobalScale createInstance(CommandManager receiver, List<String> info) {
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

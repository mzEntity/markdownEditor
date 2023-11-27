package lab.cmd.parser.console.concrete.global;

import lab.cmd.CommandManager;
import lab.cmd.cmd.GlobalScale;
import lab.cmd.parser.console.concrete.GlobalConsoleParser;
import lab.cmd.cmd.concrete.global.StatsCommand;

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

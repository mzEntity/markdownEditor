package lab1.cmd.cmd.concrete.local;

import lab1.cmd.CommandManager;
import lab1.cmd.cmd.Executable;
import lab1.cmd.cmd.GlobalScale;
import lab1.cmd.cmd.LocalScale;
import lab1.workspace.DeskTop;

public class StatsCommand implements GlobalScale {

    public static final int STATS_ALL = 0;
    public static final int STATS_CURRENT = 1;
    private CommandManager receiver;
    private Strategy strategy;

    public StatsCommand(CommandManager receiver, int kind) {
        this.receiver = receiver;
        switch (kind){
            case STATS_ALL:
                this.strategy = new StatsCommand.Strategy() {
                    @Override
                    public void execute() {
                        StatsCommand.this.receiver.showAllStat();
                    }

                    @Override
                    public String description() {
                        return "stats all";
                    }
                };
                break;
            case STATS_CURRENT:
                this.strategy = new StatsCommand.Strategy() {
                    @Override
                    public void execute() {
                        StatsCommand.this.receiver.showCurrentStat();
                    }

                    @Override
                    public String description() {
                        return "stats current";
                    }
                };
                break;
            default:
        }
    }

    @Override
    public boolean execute() {
        this.strategy.execute();
        return true;
    }

    @Override
    public String description() {
        return this.strategy.description();
    }

    private interface Strategy{
        void execute();
        String description();
    }
}

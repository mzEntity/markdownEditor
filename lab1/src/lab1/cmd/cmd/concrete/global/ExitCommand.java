package lab1.cmd.cmd.concrete.global;

import lab1.cmd.CommandManager;
import lab1.cmd.cmd.GlobalScale;

public class ExitCommand implements GlobalScale {

    private CommandManager receiver;

    public ExitCommand(CommandManager receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute() {
        this.receiver.exit();
        return true;
    }

    @Override
    public String description() {
        return "exit";
    }

}

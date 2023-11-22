package lab1.cmd.cmd.concrete.global;

import lab1.cmd.CommandManager;
import lab1.cmd.cmd.GlobalScale;

public class CloseCommand implements GlobalScale {

    private CommandManager receiver;

    public CloseCommand(CommandManager receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute() {
        this.receiver.close();
        return true;
    }

    @Override
    public String description() {
        return "exit";
    }

}

package lab.cmd.cmd.concrete.global;

import lab.cmd.CommandManager;
import lab.cmd.cmd.GlobalScale;

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

package lab.cmd.cmd.concrete.global;

import lab.cmd.CommandManager;
import lab.cmd.cmd.GlobalScale;

public class LsCommand implements GlobalScale {

    private CommandManager receiver;

    public LsCommand(CommandManager receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute() {
        this.receiver.listDirectoryContents();
        return true;
    }

    @Override
    public String description() {
        return "ls";
    }

}

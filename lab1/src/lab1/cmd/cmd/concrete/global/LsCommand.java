package lab1.cmd.cmd.concrete.global;

import lab1.cmd.CommandManager;
import lab1.cmd.cmd.GlobalScale;

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

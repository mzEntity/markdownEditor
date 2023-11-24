package lab1.cmd.cmd.concrete.global;

import lab1.cmd.CommandManager;
import lab1.cmd.cmd.GlobalScale;

public class ListWorkspaceCommand implements GlobalScale {

    private CommandManager receiver;

    public ListWorkspaceCommand(CommandManager receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute() {
        this.receiver.listWorkspace();
        return true;
    }

    @Override
    public String description() {
        return "list-workspace";
    }

}

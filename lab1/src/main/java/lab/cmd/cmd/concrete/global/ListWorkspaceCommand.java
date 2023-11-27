package lab.cmd.cmd.concrete.global;

import lab.cmd.CommandManager;
import lab.cmd.cmd.GlobalScale;

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

package lab.cmd.cmd.concrete.local;

import lab.cmd.cmd.LocalScale;
import lab.workspace.DeskTop;

public class ListCommand implements LocalScale {

    private DeskTop receiver;

    public ListCommand(DeskTop receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute() {
        return this.receiver.listLines();
    }

    @Override
    public String description() {
        return "list";
    }


}


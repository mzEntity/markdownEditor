package lab.cmd.cmd.concrete.local;

import lab.cmd.cmd.LocalScale;
import lab.workspace.DeskTop;

public class RedoCommand implements LocalScale {
    private DeskTop receiver;


    public RedoCommand(DeskTop receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute() {
        return this.receiver.redoCommand();
    }

    @Override
    public String description() {
        return "redo";
    }


}


package lab.cmd.cmd.concrete.local;

import lab.cmd.cmd.LocalScale;
import lab.workspace.DeskTop;

public class UndoCommand implements LocalScale {

    private DeskTop receiver;

    public UndoCommand(DeskTop receiver) {
        this.receiver = receiver;
    }

    @Override
    public String description() {
        return "undo";
    }

    @Override
    public boolean execute() {
        return this.receiver.undoCommand();
    }
}


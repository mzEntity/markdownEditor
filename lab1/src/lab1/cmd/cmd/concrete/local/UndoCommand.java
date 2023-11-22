package lab1.cmd.cmd.concrete.local;

import lab1.cmd.cmd.Executable;
import lab1.cmd.cmd.LocalScale;
import lab1.workspace.DeskTop;

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


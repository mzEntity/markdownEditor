package lab1.cmd.cmd.concrete;

import lab1.cmd.cmd.Executable;
import lab1.workspace.DeskTop;

public class UndoCommand implements Executable {

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


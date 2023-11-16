package lab1.cmd.cmd.concrete;

import lab1.cmd.cmd.Executable;
import lab1.workspace.DeskTop;

public class RedoCommand implements Executable {
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


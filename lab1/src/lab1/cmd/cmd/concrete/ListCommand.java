package lab1.cmd.cmd.concrete;

import lab1.cmd.cmd.Executable;
import lab1.workspace.DeskTop;

public class ListCommand implements Executable {

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


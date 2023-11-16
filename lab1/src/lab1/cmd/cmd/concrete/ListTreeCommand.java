package lab1.cmd.cmd.concrete;

import lab1.cmd.cmd.concrete.DirTreeCommand;
import lab1.workspace.DeskTop;

public class ListTreeCommand extends DirTreeCommand {
    public ListTreeCommand(DeskTop receiver) {
        super(receiver, 0);
    }

    @Override
    public boolean execute() {
        return this.receiver.showTree(this.lineNumber);
    }

    @Override
    public String description() {
        return "list-tree";
    }


}


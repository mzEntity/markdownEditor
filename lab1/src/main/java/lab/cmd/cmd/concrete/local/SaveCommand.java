package lab.cmd.cmd.concrete.local;

import lab.cmd.cmd.Unskippable;
import lab.workspace.DeskTop;

public class SaveCommand implements Unskippable {

    private DeskTop receiver;

    public SaveCommand(DeskTop receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean execute() {
        return this.receiver.saveFile();
    }

    @Override
    public String description() {
        return "save";
    }


}

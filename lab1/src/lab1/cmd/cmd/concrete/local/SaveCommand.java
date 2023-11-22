package lab1.cmd.cmd.concrete.local;

import lab1.cmd.cmd.Unskippable;
import lab1.workspace.DeskTop;

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

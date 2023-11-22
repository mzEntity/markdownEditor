package lab1.cmd.cmd.concrete;

import lab1.cmd.cmd.Unskippable;
import lab1.workspace.DeskTop;

public class LoadCommand implements Unskippable {
    private String filePath;
    private DeskTop receiver;

    public LoadCommand(DeskTop receiver, String filePath) {
        this.receiver = receiver;
        this.filePath = filePath;
    }

    @Override
    public boolean execute() {
//        this.receiver.loadFile(this.filePath);
        return true;
    }

    @Override
    public String description() {
        return "load " + this.filePath;
    }


}

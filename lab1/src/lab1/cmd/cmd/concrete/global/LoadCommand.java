package lab1.cmd.cmd.concrete.global;

import lab1.cmd.CommandManager;
import lab1.cmd.cmd.GlobalScale;
import lab1.cmd.cmd.Unskippable;
import lab1.utils.Utils;
import lab1.workspace.DeskTop;

public class LoadCommand implements GlobalScale {
    private String filePath;
    private CommandManager receiver;

    public LoadCommand(CommandManager receiver, String filePath) {
        this.receiver = receiver;
        this.filePath = Utils.getNormalizedAbsolutePath(filePath);
    }

    @Override
    public boolean execute() {
        this.receiver.load(this.filePath);
        return true;
    }

    @Override
    public String description() {
        return "load " + this.filePath;
    }

}

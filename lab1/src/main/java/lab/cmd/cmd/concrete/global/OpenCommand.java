package lab.cmd.cmd.concrete.global;

import lab.cmd.CommandManager;
import lab.cmd.cmd.GlobalScale;
import lab.utils.Utils;

public class OpenCommand  implements GlobalScale {

    private String filePath;
    private CommandManager receiver;

    public OpenCommand(CommandManager receiver, String filePath) {
        this.receiver = receiver;
        this.filePath = Utils.getNormalizedAbsolutePath(filePath);
    }

    @Override
    public boolean execute() {
        this.receiver.open(this.filePath);
        return true;
    }

    @Override
    public String description() {
        return "open " + this.filePath;
    }
}
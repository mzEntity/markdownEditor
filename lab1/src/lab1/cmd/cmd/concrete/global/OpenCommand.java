package lab1.cmd.cmd.concrete.global;

import lab1.cmd.CommandManager;
import lab1.cmd.cmd.GlobalScale;

public class OpenCommand  implements GlobalScale {

    private String filePath;
    private CommandManager receiver;

    public OpenCommand(CommandManager receiver, String filePath) {
        this.receiver = receiver;
        this.filePath = filePath;
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
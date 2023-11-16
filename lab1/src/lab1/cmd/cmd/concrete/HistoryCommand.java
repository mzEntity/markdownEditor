package lab1.cmd.cmd.concrete;

import lab1.cmd.cmd.Executable;
import lab1.workspace.DeskTop;

public class HistoryCommand implements Executable {

    private DeskTop receiver;
    private int logCount;

    public HistoryCommand(DeskTop receiver, int logCount) {
        this.receiver = receiver;
        this.logCount = logCount;
    }

    @Override
    public String description() {
        if(this.logCount == -1) return "history";
        else return "history " + this.logCount;
    }

    @Override
    public boolean execute() {
        this.receiver.showLog(this.logCount);
        return true;
    }


}


package lab.cmd.cmd.concrete.local;

import lab.cmd.cmd.LocalScale;
import lab.workspace.DeskTop;

public class HistoryCommand implements LocalScale {

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


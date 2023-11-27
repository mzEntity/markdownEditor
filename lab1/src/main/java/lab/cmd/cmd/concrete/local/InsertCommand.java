package lab.cmd.cmd.concrete.local;

import lab.cmd.cmd.Revocable;
import lab.workspace.DeskTop;

public class InsertCommand implements Revocable {

    protected long lineNumber;

    protected String prefix;
    protected String content;
    protected DeskTop receiver;

    public InsertCommand(DeskTop receiver, long lineNumber, String prefix, String content) {
        this.receiver = receiver;
        this.lineNumber = lineNumber;

        this.prefix = prefix;
        this.content = content;
    }

    @Override
    public boolean execute() {
        this.receiver.insertLine(this.lineNumber, this.prefix, this.content);
        return true;
    }



    @Override
    public void undo() {
        this.receiver.removeLine(this.lineNumber);
    }

    @Override
    public void redo() {
        this.execute();
    }

    @Override
    public String description() {
        String line;
        if(this.prefix == null) line = this.content;
        else line = this.prefix + " " + this.content;
        if(this.lineNumber == -1){
            return "insert " + line;
        } else {
            return "insert " + this.lineNumber + " " + line;
        }
    }
}


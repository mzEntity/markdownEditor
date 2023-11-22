package lab1.cmd.cmd.concrete.local;

import lab1.cmd.cmd.Executable;
import lab1.cmd.cmd.LocalScale;
import lab1.workspace.DeskTop;

public class DirTreeCommand implements LocalScale {
    protected DeskTop receiver;
    protected long lineNumber;
    protected String content;

    protected boolean specifyLineNumber;

    public DirTreeCommand(DeskTop receiver, long lineNumber) {
        this.receiver = receiver;
        this.lineNumber = lineNumber;
        this.specifyLineNumber = true;
    }

    public DirTreeCommand(DeskTop receiver, String content) {
        this.receiver = receiver;
        this.content = content;
        this.specifyLineNumber = false;
    }

    @Override
    public boolean execute() {
        if(specifyLineNumber){
            return this.receiver.showTree(this.lineNumber);
        } else {
            return this.receiver.showTree(this.content);
        }
    }

    @Override
    public String description() {
        if(this.specifyLineNumber){
            if(this.lineNumber == 0) return "dir-tree";
            else return "dir-tree " + this.lineNumber;
        } else {
            return "dir-tree " + this.content;
        }
    }


}


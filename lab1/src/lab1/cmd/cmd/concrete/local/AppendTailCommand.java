package lab1.cmd.cmd.concrete.local;

import lab1.workspace.DeskTop;

public class AppendTailCommand extends InsertCommand {

    public AppendTailCommand(DeskTop receiver, String prefix, String content) {
        super(receiver, -1, prefix, content);
    }



    @Override
    public String description() {
        if(this.prefix == null) return "append-tail " + this.content;
        return "append-tail " +  this.prefix + " " + this.content;
    }
}


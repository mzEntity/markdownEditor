package lab.cmd.cmd.concrete.local;

import lab.workspace.DeskTop;

public class AppendHeadCommand extends InsertCommand {

    public AppendHeadCommand(DeskTop receiver, String prefix, String content) {
        super(receiver, 1, prefix, content);
    }



    @Override
    public String description() {
        if(this.prefix == null) return "append-head " + this.content;
        return "append-head " +  this.prefix + " " + this.content;
    }
}


package lab1.cmd.cmd.concrete;

import lab1.cmd.cmd.Executable;
import lab1.cmd.cmd.Revocable;
import lab1.cmd.cmd.Unskippable;
import lab1.workspace.DeskTop;

public class SecretaryDecorator implements Executable {

    private DeskTop receiver;
    private Executable command;


    public SecretaryDecorator(DeskTop deskTop, Executable command) {
        this.receiver = deskTop;
        this.command = command;
    }

    @Override
    public boolean execute() {
        if(this.command instanceof Revocable){
            this.receiver.addToHistory((Revocable) this.command);
        }
        if(this.command instanceof Unskippable){
            this.receiver.clearHistory();
        }
        boolean executeSuccess = this.command.execute();
        if(executeSuccess){
            this.receiver.info(this.command.description());
            if(this.command instanceof SaveCommand){
                this.receiver.writeLogStats();
            }
        }
        return executeSuccess;
    }


    @Override
    public String description() {
        return this.command.description();
    }
}

package lab1.workspace;

import lab1.cmd.cmd.Revocable;

import java.util.Stack;

public class CommandStack {
    private Stack<Revocable> undoStack;
    private Stack<Revocable> redoStack;

    public CommandStack() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public void pushNewCommand(Revocable cmd){
        this.undoStack.push(cmd);
        this.redoStack.clear();
    }

    public void clearStack(){
        this.undoStack.clear();
        this.redoStack.clear();
    }

    public Revocable undoCommand(){
        if(this.undoStack.empty()) return null;
        Revocable cmd = this.undoStack.pop();
        this.redoStack.push(cmd);
        return cmd;
    }

    public Revocable redoCommand(){
        if(this.redoStack.empty()) return null;
        Revocable cmd = this.redoStack.pop();
        this.undoStack.push(cmd);
        return cmd;
    }
}

package lab1.cmd.cmd.concrete.local;

import lab1.cmd.cmd.Revocable;
import lab1.workspace.DeskTop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DeleteCommand implements Revocable {

    private DeskTop receiver;
    private long lineNumber;
    private String content;

    private List<Long> lineNumbers;
    private List<String> contents;

    private boolean specifyLineNumber;

    public DeleteCommand(DeskTop receiver, long lineNumber) {
        this.receiver = receiver;
        this.lineNumber = lineNumber;
        this.content = null;

        this.lineNumbers = new ArrayList<>();
        this.contents = new ArrayList<>();
        this.specifyLineNumber = true;
    }

    public DeleteCommand(DeskTop receiver, String content) {
        this.receiver = receiver;
        this.lineNumber = -1;
        this.content = content;

        this.lineNumbers = new ArrayList<>();
        this.contents = new ArrayList<>();
        this.specifyLineNumber = false;
    }

    @Override
    public boolean execute() {
        if(this.specifyLineNumber){
            this.content = this.receiver.removeLine(this.lineNumber);
            if(this.content == null) return false;
        } else {
            HashMap<Long, String> allLines = this.receiver.removeContent(this.content);
            if(allLines == null) return false;
            List<Long> sortedKeys = new ArrayList<>(allLines.keySet());
            Collections.sort(sortedKeys);
            for(Long key: sortedKeys){
                this.lineNumbers.add(key);
                this.contents.add(allLines.get(key));
            }
        }
        return true;
    }

    @Override
    public String description() {
        if(this.specifyLineNumber){
            if(this.lineNumber  == -1) return "delete-tail";
            else return "delete " + this.lineNumber;
        } else {
            return "delete " + this.content;
        }
    }



    @Override
    public void undo() {
        if(this.specifyLineNumber){
            this.receiver.insertLine(this.lineNumbers.get(0), this.contents.get(0));
        } else {
            int count = this.contents.size();
            for(int i = 0; i < count; i++){
                this.receiver.insertLine(this.lineNumbers.get(i), this.contents.get(i));
            }
        }
        this.lineNumbers = new ArrayList<>();
        this.contents = new ArrayList<>();
    }

    @Override
    public void redo() {
        this.execute();
    }
}


package lab1.cmd.cmd;

import lab1.memento.Retainable;

public interface Revocable extends LocalScale, Retainable {

    void undo();

    void redo();
}

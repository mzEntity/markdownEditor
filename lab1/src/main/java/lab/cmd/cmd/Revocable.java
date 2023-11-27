package lab.cmd.cmd;

import lab.memento.Retainable;

public interface Revocable extends LocalScale, Retainable {

    void undo();

    void redo();
}

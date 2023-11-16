package lab1.cmd.cmd;

public interface Revocable extends Executable{

    void undo();

    void redo();
}

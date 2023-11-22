package lab1.cmd.cmd;

public interface Revocable extends LocalScale{

    void undo();

    void redo();
}

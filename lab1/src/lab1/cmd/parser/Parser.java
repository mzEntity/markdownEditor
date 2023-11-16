package lab1.cmd.parser;

import lab1.workspace.DeskTop;
import lab1.cmd.cmd.Executable;

public interface Parser<T> {
    Executable createInstance(DeskTop receiver, T info);
}

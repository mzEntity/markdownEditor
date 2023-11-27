package lab1.cmd.parser.console.concrete;

import lab1.cmd.parser.console.LocalCommandParser;
import lab1.memento.Retainable;

import java.util.List;

public interface LocalConsoleParser extends LocalCommandParser<List<String>>, Retainable {
}

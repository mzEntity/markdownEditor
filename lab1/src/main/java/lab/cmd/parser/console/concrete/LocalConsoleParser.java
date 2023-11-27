package lab.cmd.parser.console.concrete;

import lab.cmd.parser.console.LocalCommandParser;
import lab.memento.Retainable;

import java.util.List;

public interface LocalConsoleParser extends LocalCommandParser<List<String>>, Retainable {
}

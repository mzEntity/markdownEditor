package lab1.cmd.parser.console.concrete;

import lab1.cmd.parser.console.GlobalCommandParser;
import lab1.memento.Retainable;

import java.util.List;

public interface GlobalConsoleParser extends GlobalCommandParser<List<String>>, Retainable {
}

package lab.cmd.parser.console.concrete;

import lab.cmd.parser.console.GlobalCommandParser;
import lab.memento.Retainable;

import java.util.List;

public interface GlobalConsoleParser extends GlobalCommandParser<List<String>>, Retainable {
}

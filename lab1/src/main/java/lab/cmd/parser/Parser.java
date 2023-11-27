package lab.cmd.parser;

public interface Parser<E, R, T> {
    E createInstance(R receiver, T info);
}

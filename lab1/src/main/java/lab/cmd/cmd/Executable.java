package lab.cmd.cmd;

public interface Executable {
    boolean execute();

    default String description(){
        return "One Command";
    }

}

package lab1.cmd.cmd;

public interface Executable {
    boolean execute();

    default String description(){
        return "One Command";
    }

}

package lab.client;

import lab.Config;
import lab.utils.Utils;

import java.io.Console;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class ConsoleManager {
    private InputManager inputManager;
    private Scanner scanner;

    private InputStream inputStream;
    private OutputStream outputStream;

    private static ConsoleManager consoleManager = new ConsoleManager();

    public static ConsoleManager getConsoleManager(){
        return consoleManager;
    }

    public static void initConsoleManager(InputStream inputStream, OutputStream outputStream){
        Config.interactionMode = false;
        consoleManager.inputStream = inputStream;
        consoleManager.outputStream = outputStream;

        if(inputStream != null){
            System.setIn(inputStream);
        }
        if(outputStream != null){
            System.setOut(new PrintStream(outputStream));
        }

        consoleManager.scanner = new Scanner(System.in);
    }

    public static void restoreConsoleManager(){
        if(consoleManager.inputStream != null){
            System.setIn(consoleManager.inputStream);
        }
        if(consoleManager.outputStream != null){
            System.setOut(new PrintStream(consoleManager.outputStream));
        }
        consoleManager.scanner = new Scanner(System.in);
    }

    private ConsoleManager(){
        this.inputManager = new InputManager();
        this.scanner = new Scanner(System.in);
    }

    public String welcomeStr(){
        StringBuilder stringBuilder = new StringBuilder("Hello World!\n");
        stringBuilder.append("Current work directory: ").append(Config.currentWorkDirectoryPathAbsolute);
        return stringBuilder.toString();
    }

    public List<String> getRequest(){
        if(Config.interactionMode){
            System.out.print(Config.interactPrompt);
        }
        String inputStr = this.getLine();
        List<String> result = this.inputManager.splitInputStr(inputStr);
        return result;
    }

   public String getLine(){
        return this.scanner.nextLine();
    }

    public static void main(String[] args) {

    }
}

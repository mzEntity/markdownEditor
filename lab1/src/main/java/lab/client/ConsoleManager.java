package lab.client;

import java.util.List;
import java.util.Scanner;

public class ConsoleManager {
    private InputManager inputManager;
    private Scanner scanner;

    public ConsoleManager() {
        this.inputManager = new InputManager();
        this.scanner = new Scanner(System.in);
    }

    public String welcomeStr(){
        return "Hello world";
    }

    public List<String> getRequest(){
        String inputStr = this.raw_input(">");
        List<String> result = this.inputManager.splitInputStr(inputStr);
        return result;
    }

    public List<String> splitInputStr(String inputStr){
        return this.inputManager.splitInputStr(inputStr);
    }

   private String raw_input(String prompt){
        System.out.print(prompt);
        String inputStr = this.scanner.nextLine();
        return inputStr;
    }

    public static void main(String[] args) {

    }
}

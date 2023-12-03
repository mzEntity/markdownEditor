package lab.client;

import lab.Config;
import lab.cmd.CommandManager;
import lab.utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private CommandManager commandManager;
    private ConsoleManager consoleManager;

    public Client() {
        this.consoleManager = ConsoleManager.getConsoleManager();
        this.commandManager = null;
    }

    private void initClient(){
        this.commandManager = new CommandManager();
        this.commandManager.initCommandManager();
    }

    private void initClient(String filePath){
        Object obj = Utils.readObject(filePath);
        this.commandManager = (CommandManager) obj;
        this.commandManager.initCommandManager();
    }

    private void readMemento(){
        String filePath = Config.mementoFilePathRelative;
        File file = new File(filePath);
        if(file.exists()){
            System.out.println("memento exists. restore.");
            this.initClient(filePath);
        }
    }

    public void startSession(){
        System.out.println(this.consoleManager.welcomeStr());
        if(Config.enableMemento){
            this.readMemento();
        }
        if(this.commandManager == null){
            this.initClient();
        }
        while(true){
            List<String> request = this.consoleManager.getRequest();
            boolean continueEnter = this.commandManager.dealWithRequest(request);
            if(!continueEnter) break;
        }
    }

    public static void main(String[] args) {
        Config.setup();
        Client client = new Client();
        client.startSession();
    }
}

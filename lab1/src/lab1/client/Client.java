package lab1.client;

import lab1.cmd.CommandManager;
import lab1.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private CommandManager commandManager;
    private ConsoleManager consoleManager;

    public Client() {
    }

    public void initClient(){
        this.consoleManager = new ConsoleManager();
        this.commandManager = new CommandManager();
        this.commandManager.initCommandManager();
    }

    public void initClient(String filePath){
        this.consoleManager = new ConsoleManager();
        Object obj = Utils.readObject(filePath);
        this.commandManager = (CommandManager) obj;
        this.commandManager.initCommandManager();
    }

    public void startSession(){
        System.out.println(this.consoleManager.welcomeStr());

        while(true){
            List<String> request = this.consoleManager.getRequest();
            boolean continueEnter = this.commandManager.dealWithRequest(request);
            if(!continueEnter) break;
        }
    }

    public void executeTest(String filePath){
        List<String> tests = this.getTestFile(filePath);
        this.executeTest(tests);
    }

    private List<String> getTestFile(String filePath){
        List<String> cmds = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = reader.readLine()) != null) {
                cmds.add(line);
            }
        } catch (IOException e) {
            System.out.println("文件不存在，新建文件" + filePath);
        }
        return cmds;
    }

    private void executeTest(List<String> cmdList){
        for(String request: cmdList){
            List<String> requestList = this.consoleManager.splitInputStr(request);
            this.commandManager.dealWithRequest(requestList);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        String filePath = "./memento";
        File file = new File(filePath);
        if(file.exists()){
            System.out.println("memento exists. restore.");
            client.initClient(filePath);
        } else {
            System.out.println("memento doesn't exist.");
            client.initClient();
        }
        client.startSession();
//        client.executeTest("test1_cmd.txt");
//        client.executeTest("test2_cmd.txt");
//        client.executeTest("test3_cmd.txt");
//        client.executeTest("test4_cmd.txt");
//        client.executeTest("test5_cmd.txt");

    }
}

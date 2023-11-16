package lab1.client;

import lab1.cmd.CommandManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private CommandManager commandManager;
    private ConsoleManager consoleManager;

    public Client() {
        this.consoleManager = new ConsoleManager();
        this.commandManager = new CommandManager();
        this.commandManager.initCommandManager();
    }

    public void startSession(){
        System.out.println(this.consoleManager.welcomeStr());

        while(true){
            List<String> request = this.consoleManager.getRequest();
            boolean continueEnter = this.respondToRequest(request);
            if(!continueEnter) break;
        }
    }

    public void executeTest(String filePath){
        List<String> tests = this.getTestFile(filePath);
        this.executeTest(tests);
    }

    private boolean respondToRequest(List<String> request){
        if(isQuit(request)){
            this.commandManager.dealWithQuit();
            return false;
        } else {
            this.commandManager.dealWithRequest(request);
            return true;
        }
    }

    private boolean isQuit(List<String> request){
        if(request.size() > 0 && request.get(0).equalsIgnoreCase("quit")){
            return true;
        } else {
            return false;
        }
    }

    private List<String> getTestFile(String filePath){
        List<String> cmds = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                cmds.add(line);
            }
        } catch (IOException e) {
            System.out.println("文件不存在，新建文件" + filePath);
        } finally {
            return cmds;
        }
    }

    private void executeTest(List<String> cmdList){
        for(String request: cmdList){
            List<String> requestList = this.consoleManager.splitInputStr(request);
            this.respondToRequest(requestList);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
//        client.startSession();
        client.executeTest("test1_cmd.txt");
        client.executeTest("test2_cmd.txt");
        client.executeTest("test3_cmd.txt");
        client.executeTest("test4_cmd.txt");
        client.executeTest("test5_cmd.txt");

    }
}

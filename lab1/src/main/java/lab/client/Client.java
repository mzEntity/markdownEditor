package lab.client;

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
        String filePath = "./memento";
        File file = new File(filePath);
        if(file.exists()){
            System.out.println("memento exists. restore.");
            this.initClient(filePath);
        } else {
            System.out.println("memento doesn't exist.");
            this.initClient();
        }
    }

    public void startSession(){
        System.out.println(this.consoleManager.welcomeStr());
        this.readMemento();
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

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try{
            File inputFile = new File("./lab2testcase/case1_input.txt");
            File outputFile = new File("./lab2testcase/case1_output.txt");

            inputStream = new FileInputStream(inputFile);
            outputStream = new FileOutputStream(outputFile);

            ConsoleManager.initConsoleManager(inputStream, outputStream);

            Client client = new Client();
            client.startSession();

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            ConsoleManager.restoreConsoleManager();
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        client.executeTest("test1_cmd.txt");
//        client.executeTest("test2_cmd.txt");
//        client.executeTest("test3_cmd.txt");
//        client.executeTest("test4_cmd.txt");
//        client.executeTest("test5_cmd.txt");

    }
}

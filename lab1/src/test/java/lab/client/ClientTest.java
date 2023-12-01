package lab.client;

import lab.Config;
import lab.utils.FileDiff;
import lab.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

public class ClientTest {

    @BeforeEach
    public void setup(){
        Utils.cleanFolder(Config.tempFolderPathRelative);
        Config.setup();
        Config.enableMemento = false;
    }

    @Test
    public void test1(){
        String fileName = "case1";
        String outputFilePath = "./lab2testcase/" + fileName + "_output.txt";
        String referenceFilePath = "./lab2testcase/" + fileName + "_refer.txt";
        this.session(fileName);
        Assertions.assertTrue(FileDiff.compare(outputFilePath, referenceFilePath));
    }

    public void session(String fileName){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String inputFilePath = "./lab2testcase/" + fileName + "_input.txt";
        String outputFilePath = "./lab2testcase/" + fileName + "_output.txt";
        try{
            File inputFile = new File(inputFilePath);
            File outputFile = new File(outputFilePath);

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
    }

    @Test
    public void test2(){
        String fileName = "case2";
        String outputFilePath = "./lab2testcase/" + fileName + "_output.txt";
        String referenceFilePath = "./lab2testcase/" + fileName + "_refer.txt";
        this.session(fileName);
        Assertions.assertTrue(FileDiff.compare(outputFilePath, referenceFilePath));
    }

    @Test
    public void test4(){
        Config.enableMemento = true;
        String fileName = "case4";
        String outputFilePath = "./lab2testcase/" + fileName + "_output.txt";
        String referenceFilePath = "./lab2testcase/" + fileName + "_refer.txt";
        this.session(fileName);
        Assertions.assertTrue(FileDiff.compare(outputFilePath, referenceFilePath));

        fileName = "case5";
        outputFilePath = "./lab2testcase/" + fileName + "_output.txt";
        referenceFilePath = "./lab2testcase/" + fileName + "_refer.txt";
        this.session(fileName);
        Assertions.assertTrue(FileDiff.compare(outputFilePath, referenceFilePath));
    }
}

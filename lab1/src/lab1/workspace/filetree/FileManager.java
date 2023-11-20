package lab1.workspace.filetree;

import java.io.*;
import java.util.*;

public class FileManager {

    private FileNode fileTree;
    private String filePath;

    public FileManager() {
        this.filePath = null;
        this.fileTree = null;
    }

    public void initFileTree(String filePath){
        this.filePath = filePath;
        this.fileTree = FileNode.getRoot();
        Map<Long, String> fileLines = this.getFileLines(filePath);
        int size = fileLines.size();
        for(long i = 0; i < size; i++){
            String line = fileLines.get(i+1);
            this.insertLine(i+1, line);
        }
    }

    public boolean insertLine(long lineNumber, String prefix, String content){
        if(this.fileTree == null){
            System.out.println("You have not load any file yet.");
            return false;
        }
        if(lineNumber == -1 || lineNumber > this.fileTree.finishNumber){
            lineNumber = this.fileTree.finishNumber + 1;
        }
        FileNode newNode = FileNode.buildFileNode(prefix, content, lineNumber);
        if(newNode == null){
            System.out.println("Invalid formatted line.");
            return false;
        }
        this.fileTree.insertChild(newNode);
        return true;
    }

    public boolean insertLine(long lineNumber, String wholeLine){
        if(this.fileTree == null){
            System.out.println("You have not load any file yet.");
            return false;
        }
        FileNode newNode = FileNode.buildFileNode(wholeLine, lineNumber);
        if(newNode == null){
            System.out.println("Invalid formatted line.");
            return false;
        }
        this.fileTree.insertChild(newNode);
        return true;
    }

    public String removeLine(long lineNumber){
        if(this.fileTree == null){
            System.out.println("You have not load any file yet.");
            return null;
        }
        if(lineNumber == -1){
            lineNumber = this.fileTree.finishNumber;
        }
        String content = this.fileTree.removeChild(lineNumber);
        if(content == null){
            System.out.println("No such line.");
            return null;
        }
        return content;
    }

    public Map<Long, String> findContent(String content){
        if(this.fileTree == null){
            System.out.println("You have not load any file yet.");
            return null;
        }
        HashMap<Long, String> allMatches = new HashMap<>();
        this.traversal(this.fileTree, new FileOperator() {
            @Override
            public void operate(FileNode fileNode) {
                if(fileNode.content.equals(content)) {
                    allMatches.put(fileNode.lineNumber, fileNode.getLine());
                }
            }
        });
        return allMatches;
    }

    public boolean saveFile(){
        if(this.filePath == null){
            return false;
        }
        return this.saveFile(this.filePath);
    }

    private boolean saveFile(String filePath){
        List<String> allFileContents = this.toFile();
        if(allFileContents == null) return false;
        this.saveFile(filePath, allFileContents);
        return true;
    }

    public List<String> toFile(){
        if(this.fileTree == null){
            System.out.println("You have not load any file yet.");
            return null;
        }
        List<String> content = new ArrayList<>();
        this.traversal(this.fileTree, new FileOperator() {
            @Override
            public void operate(FileNode fileNode) {
                content.add(fileNode.getLine());
            }
        });
        return content;
    }

    public List<String> toTree(long lineNumber){
        if(this.fileTree == null){
            System.out.println("You have not load any file yet.");
            return null;
        }
        FileNode target = this.fileTree.getNode(lineNumber);
        if(target == null) {
            System.out.println("No such line.");
            return null;
        }
        List<String> listTree = new ArrayList<>();
        if(lineNumber != 0){
            listTree.add("└── " + target.getTreeDescription());
        }
        this.traversal(target, new FileOperator() {
            List<String> finished;
            @Override
            public void operate(FileNode fileNode) {
                if(finished == null) {
                    finished = new ArrayList<>();
                    finished.add("    ");
                }
                int indentRepeat = 1;
                FileNode parent = fileNode.parent;

                while(parent != null){
                    if(parent != target){
                        parent = parent.parent;
                        indentRepeat++;
                    } else {
                        break;
                    }
                }
                boolean isCurrentFinished = (fileNode.nextSibling == null);
                String newIndent, prefix;
                if(isCurrentFinished){
                    prefix = "└── ";
                    newIndent = "    ";
                } else {
                    prefix = "├── ";
                    newIndent = "|   ";
                }
                if(indentRepeat == finished.size()){
                    finished.add(newIndent);
                } else {
                    finished.set(indentRepeat, newIndent);
                }
                StringBuilder line = new StringBuilder();
                int startIndex = lineNumber == 0 ? 1 : 0;
                for(int i = startIndex; i < indentRepeat; i++){
                    line.append(finished.get(i));
                }
                line.append(prefix + fileNode.getTreeDescription());
                listTree.add(line.toString());
            }
        });
        return listTree;
    }

    private void traversal(FileNode target, FileOperator fileOperator){
        if(target == null || target.firstChild == null) return;
        target.firstChild.accept(fileOperator);
    }

    private Map<Long, String> getFileLines(String filePath){
        Map<Long, String> lines = new HashMap<>();
        long lineNum = 1;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = reader.readLine()) != null) {
                lines.put(lineNum, line);
                lineNum++;
            }
        } catch (IOException e) {
            System.out.println("文件不存在，新建文件" + filePath);
        }
        return lines;
    }

    private void saveFile(String filePath, List<String> content){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            for (String str : content) {
                writer.write(str);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

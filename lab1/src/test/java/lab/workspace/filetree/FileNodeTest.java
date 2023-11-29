package lab.workspace.filetree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileNodeTest {
    private FileNode root;
    @BeforeEach
    public void setup(){
        this.root = FileNode.getRoot();
    }

    @Test
    public void insert1(){
        String[] prefix = {"#", "##", "###", "####", "#####"};
        String[] content = {"title1", "title2", "title3", "title4", "title5"};
        int length = prefix.length;
        for(int i = 0; i < length; i++){
            FileNode newNode = FileNode.buildFileNode(prefix[i], content[i], i+1);
            root.insertChild(newNode);
        }
        FileNode temp = root;
        for(int i = 0; i < length; i++){
            Assertions.assertNotNull(temp.firstChild);
            Assertions.assertNull(temp.nextSibling);
            Assertions.assertNull(temp.prevSibling);
            temp = temp.firstChild;
        }
        Assertions.assertNull(temp.firstChild);
    }
}

package lab1.workspace.filetree;

import java.util.regex.Pattern;

public class FileNode {
    protected FileNode parent;
    protected FileNode firstChild;
    protected FileNode prevSibling;
    protected FileNode nextSibling;

    protected long lineNumber;
    protected long finishNumber;

    protected int level;
    protected String prefix;
    protected String content;

    protected FileNode(String prefix, String content, long lineNumber, int level) {
        this.prefix = prefix;
        this.content = content;
        this.level = level;

        this.lineNumber = lineNumber;
        this.finishNumber = lineNumber;

        this.parent = null;
        this.firstChild = null;
        this.prevSibling = null;
        this.nextSibling = null;
    }

    private static final int MIN_LEVEL = 7;
    private static final int INVALID_LEVEL = -1;

    public static int getLevel(String prefix){
        if(prefix == null) return MIN_LEVEL;
        if(isOrderedListPrefix(prefix)) return MIN_LEVEL;
        int level = 0;
        switch (prefix){
            case "#":
                level = 1;
                break;
            case "##":
                level = 2;
                break;
            case "###":
                level = 3;
                break;
            case "####":
                level = 4;
                break;
            case "#####":
                level = 5;
                break;
            case "######":
                level = 6;
                break;
            case "-":
            case "+":
            case "*":
                level = MIN_LEVEL;
                break;
            default:
                level = INVALID_LEVEL;
                break;
        }
        return level;
    }
    private static boolean isOrderedListPrefix(String prefix){
        if(prefix == null) return false;
        int length = prefix.length();
        if(length <= 1) return false;
        if(prefix.charAt(length-1) != '.') return false;
        String subStr = prefix.substring(0, length-1);
        if(Pattern.matches("\\d+", subStr)){
            return true;
        } else {
            return false;
        }
    }

    public static FileNode getRoot(){
        return new FileCompositeNode(null, "ROOT", 0, 0);
    }

    public final static FileNode buildFileNode(String line, long lineNumber){
        String prefix, mainBody;
        int level;
        if(line.contains(" ")){
            String[] split = line.split(" ", 2);
            prefix = split[0];
            level = FileNode.getLevel(prefix);
            if(level == INVALID_LEVEL){
                prefix = null;
                mainBody = line;
            } else {
                mainBody = split[1];
            }
        } else {
            level = MIN_LEVEL;
            prefix = null;
            mainBody = line;
        }

        return buildFileNode(prefix, mainBody, lineNumber, level);
    }

    public final static FileNode buildFileNode(String prefix, String mainBody, long lineNumber){
        int level = getLevel(prefix);
        return buildFileNode(prefix, mainBody, lineNumber, level);
    }

    private final static FileNode buildFileNode(String prefix, String mainBody, long lineNumber, int level){
        if(level == INVALID_LEVEL) return null;
        if(level < MIN_LEVEL){
            return new FileCompositeNode(prefix, mainBody, lineNumber, level);
        } else {
            return FileNode.buildFileLeafNode(prefix, mainBody, lineNumber, level);
        }
    }

    private final static FileNode buildFileLeafNode(String prefix, String mainBody, Long lineNumber, int level){
        if(prefix == null){
            return new TextNode(prefix, mainBody, lineNumber, level);
        }
        if(isOrderedListPrefix(prefix)){
            return new OrderedListNode(prefix, mainBody, lineNumber, level);
        }
        switch (prefix){
            case "-":
            case "+":
            case "*":
                return new UnorderedListNode(prefix, mainBody, lineNumber, level);
            default:
                return new TextNode(prefix, mainBody, lineNumber, level);
        }
    }

    public void insertChild(FileNode newNode){ return; }

    public String removeChild(long startLineNum){ return null; }

    public void accept(FileOperator fileOperator){
        fileOperator.operate(this);
        if(this.firstChild != null){
            this.firstChild.accept(fileOperator);
        }
        if(this.nextSibling != null){
            this.nextSibling.accept(fileOperator);
        }
    }

    public String getLine(){
        if(this.prefix == null) return this.content;
        else return this.prefix + " " + this.content;
    }

    public String getTreeDescription(){
        return this.prefix + " " + this.content;
    }

    protected void changePosition(long startLineNumber, long offset){
        assert (this.finishNumber >= startLineNumber);
        if(this.lineNumber >= startLineNumber){
            this.lineNumber += offset;
        }
        this.finishNumber += offset;
        FileNode child = this.firstChild;
        while(child != null){
            if(child.finishNumber >= startLineNumber){
                child.changePosition(startLineNumber, offset);
            }
            child = child.nextSibling;
        }
    }

    protected void backwardPosition(long startLineNumber, long occupiedLinesCount){
        changePosition(startLineNumber, occupiedLinesCount);
    }

    protected void forwardPosition(long startLineNumber, long occupiedLinesCount){
        changePosition(startLineNumber, -occupiedLinesCount);
    }

    protected void stripSiblingAndParent(){
        if(this.parent != null){
            if(this.parent.firstChild == this){
                this.parent.firstChild = this.nextSibling;
            }
        }
        if(this.prevSibling != null) this.prevSibling.nextSibling = this.nextSibling;
        if(this.nextSibling != null) this.nextSibling.prevSibling = this.prevSibling;
        this.prevSibling = this.nextSibling = this.parent = null;
    }

    protected boolean setNextSibling(FileNode newNode){
        if(newNode == null || this.canAdopt(newNode)) return false;
        if(newNode.canAdopt(this.nextSibling)) return false;

        newNode.stripSiblingAndParent();
        this.splitToNextNode(newNode);

        if(this.nextSibling != null){
            this.nextSibling.prevSibling = newNode;
        }

        newNode.nextSibling = this.nextSibling;
        newNode.prevSibling = this;
        newNode.parent = this.parent;

        this.nextSibling = newNode;
        return true;
    }

    protected boolean setPrevSibling(FileNode newNode){
        if(newNode == null || newNode.canAdopt(this)) return false;

        newNode.stripSiblingAndParent();
        newNode.splitToNextNode(this);

        if(this.prevSibling != null){
            this.prevSibling.nextSibling = newNode;
        } else {
            this.parent.firstChild = newNode;
        }

        newNode.nextSibling = this;
        newNode.prevSibling = this.prevSibling;
        newNode.parent = this.parent;

        this.prevSibling = newNode;
        return true;
    }

    protected boolean adoptChild(FileNode newNode){
        return false;
    }

    protected boolean abandonChild(FileNode targetNode){ return false; }

    protected FileNode getNode(long startLineNum){
        return null;
    }

    protected long splitToNextNode(FileNode newNode){
        return this.finishNumber;
    }

    protected boolean hasChild(){
        return this.firstChild != null;
    }

    protected long occupiedLineCount(){
        return this.finishNumber - this.lineNumber + 1;
    }

    protected boolean canAdopt(FileNode targetChild){
        return false;
    }

    protected boolean locatePrev(FileNode targetNode){
        if(targetNode == null) return false;
        return this.lineNumber < targetNode.lineNumber;
    }

    protected boolean finishPrev(FileNode targetNode){
        if(targetNode == null) return false;
        return this.finishNumber < targetNode.lineNumber;
    }

    protected String getScope(){
        return "(" + this.lineNumber + ", " + this.finishNumber + ")";
    }
}
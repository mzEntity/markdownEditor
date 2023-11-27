package lab.workspace.filetree;

public class FileLeafNode extends FileNode{
    protected FileLeafNode(String prefix, String content, long lineNumber, int level) {
        super(prefix, content, lineNumber, level);
    }

    @Override
    protected boolean hasChild() {
        return false;
    }

    protected FileNode getNode(long startLineNum){
        if(this.lineNumber == startLineNum) return this;
        else return null;
    }

    @Override
    public String toString() {
        return this.getScope() + ": " + content + "\n";
    }

}

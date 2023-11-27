package lab.workspace.filetree;

public class TextNode extends FileLeafNode{
    protected TextNode(String prefix, String content, long lineNumber, int level) {
        super(prefix, content, lineNumber, level);
    }

    @Override
    public String getTreeDescription() {
        return this.content;
    }
}

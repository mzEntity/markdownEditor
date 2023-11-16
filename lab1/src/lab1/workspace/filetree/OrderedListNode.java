package lab1.workspace.filetree;

public class OrderedListNode extends FileLeafNode{
    protected OrderedListNode(String prefix, String content, long lineNumber, int level) {
        super(prefix, content, lineNumber, level);
    }

    @Override
    public String getTreeDescription() {
        int index = 1;
        FileNode sibling = this.prevSibling;
        while(sibling != null){
            if(this.prevSibling instanceof OrderedListNode){
                index++;
            } else {
                break;
            }
            sibling = sibling.prevSibling;
        }
        return index + ". " + this.content;
    }

    @Override
    public String getLine() {
        return this.getTreeDescription();
    }
}

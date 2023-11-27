package lab.workspace.filetree;

public class FileCompositeNode extends FileNode{
    protected FileCompositeNode(String prefix, String content, long lineNumber, int level) {
        super(prefix, content, lineNumber, level);
    }

    @Override
    public void insertChild(FileNode newNode){
        long startLineNumber = newNode.lineNumber;
        long occupiedLinesCount = newNode.occupiedLineCount();
        this.backwardPosition(startLineNumber, occupiedLinesCount);
        this.adoptChild(newNode);
    }

    @Override
    public String removeChild(long startLineNum){
        FileNode node = this.getNode(startLineNum);
        if(node == null){
            return null;
        }
        String content = node.getLine();
        this.abandonChild(node);
        this.forwardPosition(startLineNum, 1);
        return content;
    }

    @Override
    public boolean adoptChild(FileNode newNode) {
        if(newNode == null) return false;
        if(!this.canAdopt(newNode)) return false;

        if(this.finishNumber < newNode.finishNumber){
            this.finishNumber = newNode.finishNumber;
        }

        newNode.stripSiblingAndParent();

        if(!this.hasChild()){
            this.firstChild = newNode;
            newNode.parent = this;
            newNode.prevSibling = null;
            newNode.nextSibling = null;
        } else {
            FileNode child = this.firstChild;
            if(child.locatePrev(newNode)){
                while(child.finishPrev(newNode)){
                    FileNode nextSibling = child.nextSibling;
                    if(nextSibling == null || !nextSibling.locatePrev(newNode)){
                        break;
                    }
                    child = nextSibling;
                }
                if(child.canAdopt(newNode)){
                    child.adoptChild(newNode);
                } else {
                    FileNode sibling = child.nextSibling;
                    while(newNode.canAdopt(sibling)){
                        FileNode temp = sibling.nextSibling;
                        newNode.adoptChild(sibling);
                        sibling = temp;
                    }
                    child.setNextSibling(newNode);
                }
            } else {
                if(!newNode.canAdopt(child)){
                    child.setPrevSibling(newNode);
                } else {
                    while(newNode.canAdopt(child)){
                        FileNode nextSibling = child.nextSibling;
                        newNode.adoptChild(child);
                        child = nextSibling;
                    }
                    if(child == null){
                        newNode.prevSibling = null;
                        newNode.nextSibling = null;
                        newNode.parent = this;
                        this.firstChild = newNode;
                    }
                    else {
                        child.setPrevSibling(newNode);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean abandonChild(FileNode targetNode){
        FileNode parent = targetNode.parent;
        if(parent == null){
            return false;
        }
        targetNode.stripSiblingAndParent();
        FileNode child = targetNode.firstChild;
        while(child != null){
            FileNode nextChild = child.nextSibling;
            parent.adoptChild(child);
            child = nextChild;
        }
        return true;
    }

    @Override
    protected FileNode getNode(long startLineNum){
        if(this.lineNumber > startLineNum || this.finishNumber < startLineNum){
            return null;
        }
        if(this.lineNumber == startLineNum) return this;
        FileNode child = this.firstChild;
        while(child != null){
            FileNode node = child.getNode(startLineNum);
            if(node != null){
                return node;
            } else {
                child = child.nextSibling;
            }
        }
        return null;
    }

    @Override
    protected long splitToNextNode(FileNode newNode){
        if(!this.locatePrev(newNode) || this.finishPrev(newNode)) return this.finishNumber;
        long newFinishNumber = this.lineNumber;
        FileNode child = this.firstChild;
        while(child != null){
            FileNode sibling = child.nextSibling;
            if(!child.finishPrev(newNode)){
                if(child.locatePrev(newNode)){
                    newFinishNumber = child.splitToNextNode(newNode);
                }else{
                    if(newNode.canAdopt(child)){
                        newNode.adoptChild(child);
                    } else {
                        return this.finishNumber;
                    }
                }
            } else {
                newFinishNumber = child.finishNumber;
            }
            child = sibling;
        }

        this.finishNumber = newFinishNumber;
        return newFinishNumber;
    }

    @Override
    protected boolean canAdopt(FileNode targetChild){
        if(targetChild == null) return false;
        return this.level < targetChild.level;
    }

    @Override
    public String toString() {
        String result = this.getScope() + ": " + content + "\n";
        FileNode child = this.firstChild;
        while(child != null){
            result += this.getScope() + "'s kid: " + child;
            child = child.nextSibling;
        }
        return result;
    }

    @Override
    public String getTreeDescription() {
        return this.content;
    }
}

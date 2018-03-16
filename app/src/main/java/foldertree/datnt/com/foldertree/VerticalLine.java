package foldertree.datnt.com.foldertree;

/**
 * Created by DatNT on 3/15/2018.
 */

public class VerticalLine extends Node {
    private int folderBelong;

    public VerticalLine(int id, int division,int parent, int folderBelong) {
        super(id, division,parent);
        this.folderBelong = folderBelong;
    }

    public int getFolderBelong() {
        return folderBelong;
    }

    public void setFolderBelong(int folderBelong) {
        this.folderBelong = folderBelong;
    }
}

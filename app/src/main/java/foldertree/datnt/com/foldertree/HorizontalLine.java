package foldertree.datnt.com.foldertree;

/**
 * Created by DatNT on 3/15/2018.
 */

public class HorizontalLine extends Node{
    private int folderBelong;

    public HorizontalLine(int id, int division, int parent, int folderBelong) {
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

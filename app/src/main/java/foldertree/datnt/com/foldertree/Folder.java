package foldertree.datnt.com.foldertree;

import java.util.ArrayList;

/**
 * Created by DatNT on 3/14/2018.
 */

public class Folder extends Node{
    private String path;
    private String name;
    private int margin;
    private int rightSibling;
    private int leftSibling;
    private ArrayList<Node> subFolder;
    public Folder(int id,int division, int parent, String path, String name) {
        super(id, division,parent);
        this.path = path;
        this.name = name;
        this.parent = parent;
        this.rightSibling = -1;
        this.leftSibling = -1;
    }

    public Folder(Folder folder){
        super();
        this.id = folder.getId();
        this.path = folder.getPath();
        this.name = folder.getName();
        this.parent = folder.getParent();
        this.division = folder.getDivision();
        this.rightSibling = folder.getRightSibling();
        this.leftSibling = folder.getLeftSibling();
    }
    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public int getRightSibling() {
        return rightSibling;
    }

    public void setRightSibling(int rightSibling) {
        this.rightSibling = rightSibling;
    }

    public int getLeftSibling() {
        return leftSibling;
    }

    public void setLeftSibling(int leftSibling) {
        this.leftSibling = leftSibling;
    }

    public String getPath() {
        return path;
    }

    public ArrayList<Node> getSubFolder() {
        return subFolder;
    }

    public void setSubFolder(ArrayList<Node> subFolder) {
        this.subFolder = subFolder;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

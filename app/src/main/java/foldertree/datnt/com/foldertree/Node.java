package foldertree.datnt.com.foldertree;

/**
 * Created by DatNT on 3/15/2018.
 */

public class Node {
    protected int id;
    protected int division;
    protected int parent;

    public Node(int id, int division, int parent) {
        this.id = id;
        this.division = division;
        this.parent = parent;
    }

    public Node() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDivision() {
        return division;
    }

    public void setDivision(int division) {
        this.division = division;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }
}

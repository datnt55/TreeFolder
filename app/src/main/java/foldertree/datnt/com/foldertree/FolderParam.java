package foldertree.datnt.com.foldertree;

import android.view.View;

/**
 * Created by DatNT on 3/14/2018.
 */

public class FolderParam {
    private int id;
    private View view;
    private Node node;
    private int division;

    public FolderParam(View view, Node node) {
        this.view = view;
        this.node = node;
        this.id = node.getId();
        this.division = node.getDivision();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
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
}

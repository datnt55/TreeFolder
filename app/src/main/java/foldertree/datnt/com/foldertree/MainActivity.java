package foldertree.datnt.com.foldertree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Folder> folders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Folder folder1 = new Folder(1,2,103, "","");
        Folder folder2 = new Folder(2,2,103,"","");
        Folder folder3 = new Folder(3,2,103,"","");
        Folder folder4 = new Folder(4,2,103,"","");
        Folder folder5 = new Folder(5,2,103,"","");

        Folder folder6 = new Folder(6,3,3,"","");
        Folder folder7 = new Folder(7,3,3,"","");
        Folder folder8 = new Folder(8,3,3,"","");




        Folder folder9 = new Folder(9,4,8,"","");
        Folder folder10 = new Folder(10,4,8,"","");
        Folder folder11 = new Folder(11,4,8,"","");


        Folder folder12 = new Folder(12,3,4,"","");
        Folder folder13 = new Folder(13,3,4,"","");
        Folder folder14 = new Folder(14,3,4,"","");

        Folder root = new Folder(103,1, 0, "","");

        folders = new ArrayList<>();
        folders.add(root);
        folders.add(folder1);
        folders.add(folder2);
        folders.add(folder3);
        folders.add(folder4);
        folders.add(folder5);
        folders.add(folder6);
        folders.add(folder7);
        folders.add(folder8);

        folders.add(folder12);
        folders.add(folder13);
        folders.add(folder14);
        FolderCustomView folderCustomView = findViewById(R.id.layout_folder);
        identityFolder();
        folderCustomView.startDraw(folders);
    }

    private void identityFolder() {
        for (int i = 1; i < 5; i++) {
            for (Folder folder : folders) {
                if (folder.getDivision() == i) {
                    ArrayList<Folder> subFolder = new ArrayList<>();
                    for (Folder f : folders)
                        if (f.getParent() == folder.getId()) {
                            subFolder.add(f);
                            if (subFolder.size() < 2)
                                f.setLeftSibling(-1);
                            else {
                                subFolder.get(subFolder.size() - 2).setRightSibling(f.getId());
                                f.setLeftSibling(subFolder.get(subFolder.size() - 2).getId());
                            }
                        }
                }
            }
        }
    }
}

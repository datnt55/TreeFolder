package foldertree.datnt.com.foldertree;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by DatNT on 3/14/2018.
 */

public class FolderCustomView extends RelativeLayout {
    private Context mContext;
    private ArrayList<Folder> folders;
    private RelativeLayout layoutRoot;
    private ArrayList<FolderParam> listViewInRoot;
    private FolderView rootFolder;
    private int offset = 0;
    public FolderCustomView(Context context) {
        super(context);
        this.mContext = context;
        initLayout();
    }

    public FolderCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initLayout();
    }

    public FolderCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initLayout();
    }

    public void startDraw(ArrayList<Folder> folders) {
        this.folders = folders;
        for (Folder folder : folders) {
            if (folder.getParent() == 0) {
                rootFolder = new FolderView(mContext);
                rootFolder.setId(folder.getId());
                rootFolder.setTitle(folder.getId() + "");
                layoutRoot.addView(rootFolder);
                listViewInRoot.add(new FolderParam(rootFolder, folder));
                ArrayList<Folder> subFolder = new ArrayList<>();
                for (Folder f : folders)
                    if (f.getParent() == folder.getId()) {

                        subFolder.add(f);
                    }
                if (subFolder.size() > 0)
                    invalidateTree(folder, subFolder, true);
                break;
            }
        }

        for (int i = 2; i < 5; i++) {
            for (Folder folder : folders) {
                if (folder.getDivision() == i) {
                    ArrayList<Folder> subFolder = new ArrayList<>();
                    for (Folder f : folders)
                        if (f.getParent() == folder.getId())
                            subFolder.add(f);
                    if (subFolder.size() > 0)
                        invalidateTree(folder, subFolder, false);
                }
            }
        }

    }

    private void initLayout() {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.folder_custom_view, this, true);
        layoutRoot = view.findViewById(R.id.root);
        listViewInRoot = new ArrayList<>();
    }

    private void invalidateTree(Folder folder, ArrayList<Folder> subFolder, boolean root) {
        // Create vertical cross
        // If folder is root, set cross always center horizontal
        // If folder is subfolder, set margin of cross is margin of folder plus 35dp
        View verticalCross = new View(mContext);
        verticalCross.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        verticalCross.setId(folder.getId() * 1000 + 6789);
        layoutRoot.addView(verticalCross);
        RelativeLayout.LayoutParams crossVParam = new LayoutParams((int) convertDpToPixel(1, mContext), (int) convertDpToPixel(20, mContext));
        crossVParam.addRule(BELOW, folder.getId());
        if (folder.getMargin() != 0)
            crossVParam.leftMargin = folder.getMargin() + (int) convertDpToPixel(35, mContext);
        verticalCross.setLayoutParams(crossVParam);
        listViewInRoot.add(new FolderParam(verticalCross, new VerticalLine(verticalCross.getId(),folder.getDivision(),folder.getId(), folder.getId())));

        // Create horizontal cross
        View horizontalCross = new View(mContext);
        int horizontalCrossMargin;
        horizontalCross.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        horizontalCross.setId(folder.getId() * 1000 + 4567);
        layoutRoot.addView(horizontalCross);
        if (offset == 0)
            offset = (int) convertDpToPixel(100, mContext);
        int crossMargin = 0;
        if (folder.getMargin() != 0) {
            crossMargin = folder.getMargin() + (int) convertDpToPixel(35, mContext) - offset * (subFolder.size() - 1) / 2;
            if (crossMargin < 0) {
                invalidateAllComponentOfTree(-crossMargin + (int) convertDpToPixel(50, mContext));
                crossMargin = (int) convertDpToPixel(50, mContext);
            }
        }
        RelativeLayout.LayoutParams crossParam = new LayoutParams(offset * (subFolder.size() - 1), (int) convertDpToPixel(1, mContext));
        crossParam.addRule(BELOW, verticalCross.getId());
        if (folder.getMargin() != 0) {
            //int crossMargin = folder.getMargin() + (int) convertDpToPixel(35, mContext) - offset * (folder.getSubFolders().size() - 1) / 2;
            crossParam.leftMargin = crossMargin;
            horizontalCrossMargin = crossMargin;
        } else {
            horizontalCrossMargin = (int) convertDpToPixel(50, mContext);
            crossParam.leftMargin = (int) convertDpToPixel(50, mContext);
            crossParam.rightMargin = (int) convertDpToPixel(50, mContext);
        }
        horizontalCross.setLayoutParams(crossParam);
        listViewInRoot.add(new FolderParam(horizontalCross, new HorizontalLine(horizontalCross.getId(),folder.getDivision(),folder.getId(), folder.getId())));

        if (root) {
            RelativeLayout.LayoutParams params = (LayoutParams) rootFolder.getLayoutParams();
            params.leftMargin = horizontalCrossMargin + offset * (subFolder.size() - 1) / 2 - (int) convertDpToPixel(35, mContext);
            rootFolder.setLayoutParams(params);

            crossVParam = (LayoutParams) verticalCross.getLayoutParams();
            crossVParam.leftMargin = horizontalCrossMargin + offset * (subFolder.size() - 1) / 2;
            verticalCross.setLayoutParams(crossVParam);
        }
        // Add subfolder
        ArrayList<Node> children = new ArrayList<>();
        children.add(new VerticalLine(verticalCross.getId(),folder.getDivision(),folder.getId(), folder.getId()));
        children.add(new HorizontalLine(horizontalCross.getId(),folder.getDivision(),folder.getId(), folder.getId()));
        for (int i = 0; i < subFolder.size(); i++) {
            View verticalDash;
            verticalDash = new View(mContext);
            verticalDash.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
            verticalDash.setId(subFolder.get(i).getId() + 9876);
            layoutRoot.addView(verticalDash);
            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams((int) convertDpToPixel(1, mContext), (int) convertDpToPixel(20, mContext));
            param.addRule(RelativeLayout.BELOW, horizontalCross.getId());
            param.leftMargin = horizontalCrossMargin + offset * i;
            verticalDash.setLayoutParams(param);
            listViewInRoot.add(new FolderParam(verticalDash, new VerticalLine(verticalDash.getId(),subFolder.get(i).getDivision(),subFolder.get(i).getParent(), subFolder.get(i).getId())));
            children.add( new VerticalLine(verticalDash.getId(),subFolder.get(i).getDivision(),subFolder.get(i).getParent(), subFolder.get(i).getId()));

            FolderView folderView = new FolderView(mContext);
            folderView.setId(subFolder.get(i).getId());
            folderView.setTitle(subFolder.get(i).getId() + "");
            layoutRoot.addView(folderView);
            RelativeLayout.LayoutParams paramFolder = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Log.e("ID", verticalDash.getId() + "");
            paramFolder.addRule(BELOW, verticalDash.getId());
            paramFolder.leftMargin = horizontalCrossMargin + offset * i - (int) convertDpToPixel(35, mContext);
            folderView.setLayoutParams(paramFolder);
            subFolder.get(i).setMargin(horizontalCrossMargin + offset * i - (int) convertDpToPixel(35, mContext));
            listViewInRoot.add(new FolderParam(folderView, subFolder.get(i)));
            children.add(subFolder.get(i));
        }
        folder.setSubFolder(children);
        Folder rightSibling = getFolderById(folder.getRightSibling());
        Folder leftSibling = getFolderById(folder.getLeftSibling());
        if (leftSibling != null && leftSibling.getSubFolder()!= null){
            int margin = Integer.MIN_VALUE;
            for (Node node : leftSibling.getSubFolder())
                if (node instanceof  Folder)
                    if ( ((Folder) node).getMargin() > margin)
                        margin = ((Folder) node).getMargin();
            Folder sub = subFolder.get(0);
            if (margin > sub.getMargin()){
                margin = margin - sub.getMargin() + offset;
                CheckLeftChildFolder(folder,margin );
            }
        }
//        int marginOffset = 0;
//        Folder index = null;
//        if (rightSibling != null) {
//            Folder sub = subFolder.get(subFolder.size()-1);
//            if (sub.getMargin() >= rightSibling.getMargin() - (int) convertDpToPixel(35, mContext) ) {
//                marginOffset = sub.getMargin() -  rightSibling.getMargin()+ (int) convertDpToPixel(35, mContext) ;
//                //CheckLeftAndRightParent(sub,offerMargin + (int) convertDpToPixel(35, mContext) );
//                index = sub;
//            }
//        }
//
//        if (leftSibling != null) {
//            Folder sub = subFolder.get(0);
//            if (sub.getMargin() <= leftSibling.getMargin()) {
//                int margin = leftSibling.getMargin() - sub.getMargin() + (int) convertDpToPixel(35, mContext) ;
//                if (margin > marginOffset ) {
//                    marginOffset = margin;
//                    index = sub;
//                }
//                //CheckLeftAndRightParent(sub,offerMargin + (int) convertDpToPixel(35, mContext) );
//
//            }
//        }
//
//        if (marginOffset != 0 && index != null){
//            CheckLeftAndRightParent(index,marginOffset + (int) convertDpToPixel(35, mContext));
//        }


    }

    private void CheckLeftChildFolder(Folder folder, int offerMargin) {
        for (Node node : folder.getSubFolder()){
            FolderParam folderParam = getViewOfFolder(node.getId());
            RelativeLayout.LayoutParams params = (LayoutParams) folderParam.getView().getLayoutParams();
            if (folderParam.getNode() instanceof Folder)
                ((Folder) folderParam.getNode()).setMargin( params.getMarginStart() + offerMargin);
            params.leftMargin = params.getMarginStart() + offerMargin;
            folderParam.getView().setLayoutParams(params);
        }


    }

    private Folder getFolderById(int id){
        for (Folder folder : folders)
            if (folder.getId() == id)
                return folder;
        return null;
    }
    private void invalidateAllComponentOfTree(int offerMargin) {
        for (FolderParam view : listViewInRoot) {
            RelativeLayout.LayoutParams params = (LayoutParams) view.getView().getLayoutParams();
            params.leftMargin = params.getMarginStart() + offerMargin;
            view.getView().setLayoutParams(params);
            if (view.getNode() != null && view.getNode() instanceof  Folder) {
                Folder folder = (Folder) view.getNode();
                folder.setMargin(folder.getMargin() + offerMargin);
            }
        }

    }

    private FolderParam getViewOfFolder(int id){
        for (FolderParam view : listViewInRoot) {
            if (view.getNode() != null) {
                if (view.getNode().getId() == id)
                    return view;
            }
        }
        return null;
    }

    private FolderParam getLineOfFolder(int id){
        for (FolderParam view : listViewInRoot) {
            if (view.getNode() != null && view.getNode() instanceof  VerticalLine) {
                if (((VerticalLine) view.getNode()).getFolderBelong() == id)
                    return view;
            }
        }
        return null;
    }

    private void CheckLeftAndRightParent(Folder folder, int offerMargin){
        ArrayList<FolderParam> leftDivision = new ArrayList<>();
        ArrayList<FolderParam> rightDivision = new ArrayList<>();
        Folder parentFolder = new Folder(getFolderById(folder.getParent()));
        while (parentFolder.getParent() != 0){
            Folder temp = new Folder(parentFolder);
            while (temp.getLeftSibling() != -1){
                Folder left = getFolderById(temp.getLeftSibling());
//                if (left.getSubFolder() != null){
//                    for (Node node : left.getSubFolder()) {
//                        FolderParam leftFolderParam = getViewOfFolder(node.getId());
//                        if (leftDivision != null)
//                            leftDivision.add(leftFolderParam);
//                    }
//                }
                if (left.getId() != folder.getId()) {
                    FolderParam leftFolderParam = getViewOfFolder(left.getId());
                    if (leftDivision != null)
                        leftDivision.add(leftFolderParam);
                    FolderParam leftLineParam = getLineOfFolder(left.getId());
                    if (leftDivision != null)
                        leftDivision.add(leftLineParam);
                }
                temp = left;
            }

            temp = new Folder(parentFolder);
            while (temp.getRightSibling() != -1){
                Folder right = getFolderById(temp.getRightSibling());
//                if (right.getSubFolder() != null){
//                    for (Node node : right.getSubFolder()) {
//                        FolderParam rightFolderParam = getViewOfFolder(node.getId());
//                        if (rightFolderParam != null)
//                            rightDivision.add(rightFolderParam);
//                    }
//                }
                FolderParam rightFolderParam = getViewOfFolder(right.getId());
                if (rightFolderParam != null)
                    rightDivision.add(rightFolderParam);
                FolderParam rightLineParam = getLineOfFolder(right.getId());
                if (rightDivision != null)
                    rightDivision.add(rightLineParam);
                temp = right;
            }
            parentFolder = getFolderById(parentFolder.getParent());
//            FolderParam rightFolderParam = getViewOfFolder(parentFolder.getId());
//            FolderParam rightLineParam = getLineOfFolder(parentFolder.getId());
//            if (rightFolderParam != null)
//                leftDivision.add(rightFolderParam);
//            if (rightLineParam != null)
//                leftDivision.add(rightLineParam);
        }
        invalidateANodeOfTree(leftDivision, rightDivision,offerMargin);
    }

    private void invalidateANodeOfTree(ArrayList<FolderParam> lefts, ArrayList<FolderParam> rights, int offerMargin){
        int newOffset = offset + offerMargin;
        for (int i = 2; i < 5; i++) {
            ArrayList<FolderParam> subFolder = new ArrayList<>();
            for (FolderParam v : rights) {
                if (v.getNode().getDivision() == i)
                    subFolder.add(v);
            }
            if (subFolder.size() != 0) {
                int countLine = 1;
                int count = 1;
                for (FolderParam v : subFolder) {
                    if (v.getNode() instanceof VerticalLine) {
                        RelativeLayout.LayoutParams params = (LayoutParams) v.getView().getLayoutParams();
                        params.leftMargin = params.getMarginStart() + offerMargin*countLine;
                        v.getView().setLayoutParams(params);
                        countLine ++;
                    }
                    if (v.getNode() instanceof Folder) {
                        RelativeLayout.LayoutParams params = (LayoutParams) v.getView().getLayoutParams();
                        ((Folder) v.getNode()).setMargin(params.getMarginStart() + offerMargin*count);
                        params.leftMargin = params.getMarginStart() + offerMargin*count;
                        v.getView().setLayoutParams(params);
                       // invalidateSubFolder(v, offerMargin*count);
                        count ++;
                    }
                }
            }
        }

        for (int i = 2; i < 5; i++) {
            ArrayList<FolderParam> subFolder = new ArrayList<>();
            int countFolder = 0;
            for (FolderParam f : listViewInRoot) {
                if (f.getNode() instanceof Folder)
                    if (f.getNode().getDivision() == i)
                        countFolder ++;
            }
            for (FolderParam v : lefts) {
                if (v.getDivision() == i)
                    subFolder.add(v);
            }
            if (subFolder.size() != 0) {
                int countLine = 1;
                int count = 1;
                int minMargin = Integer.MAX_VALUE;
                for (FolderParam v : subFolder) {
                    if (v.getNode() instanceof VerticalLine) {
                        RelativeLayout.LayoutParams params = (LayoutParams) v.getView().getLayoutParams();
                        params.leftMargin = params.getMarginStart() - offerMargin*countLine;
                        v.getView().setLayoutParams(params);
                        countLine ++;
                    }
                    if (v.getNode() instanceof Folder) {
                        RelativeLayout.LayoutParams params = (LayoutParams) v.getView().getLayoutParams();
                        ((Folder) v.getNode()).setMargin(params.getMarginStart() - offerMargin*count);
                        if (minMargin > (params.getMarginStart() - offerMargin*count))
                            minMargin = params.getMarginStart() - offerMargin*count;
                        params.leftMargin = params.getMarginStart() - offerMargin*count ;
                        v.getView().setLayoutParams(params);
                        count ++;
                        //invalidateSubFolder(v, -offerMargin*count);
                    }
                }
                for (FolderParam f : listViewInRoot) {
                    if (f.getNode() instanceof HorizontalLine && f.getNode().getDivision() == i - 1) {
                        RelativeLayout.LayoutParams params = (LayoutParams) f.getView().getLayoutParams();
                        params.width = newOffset * (countFolder - 1);
                        params.leftMargin = minMargin + (int) convertDpToPixel(35, mContext);
                        f.getView().setLayoutParams(params);
                        break;
                    }
                }
                if (minMargin < 0)
                    invalidateAllComponentOfTree(- minMargin + (int) convertDpToPixel(50, mContext) );
            }
        }


    }

    private void invalidateSubFolder(FolderParam v, int margin) {
        for (FolderParam f : listViewInRoot) {
            if (f.getNode().getParent() == v.getNode().getId()){
                RelativeLayout.LayoutParams params = (LayoutParams) f.getView().getLayoutParams();
                if (f.getNode() instanceof Folder ){
                    ((Folder) f.getNode()).setMargin(params.getMarginStart() + margin);
                }
                params.leftMargin = params.getMarginStart() + margin ;
                f.getView().setLayoutParams(params);
            }
        }
    }


    public static int getMeasureOfView(final View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}

package foldertree.datnt.com.foldertree;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
public class FolderView extends RelativeLayout{
    private Context mContext;
    private TextView txtTitle;
    public FolderView(Context context) {
        super(context);
        this.mContext = context;
        initLayout();
    }

    public FolderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initLayout();
    }

    public FolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initLayout();
    }
    private void initLayout() {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.folder_view, this, true);
        txtTitle = view.findViewById(R.id.title);

    }

    public void setTitle(String title){
        txtTitle.setText(title);
    }
}
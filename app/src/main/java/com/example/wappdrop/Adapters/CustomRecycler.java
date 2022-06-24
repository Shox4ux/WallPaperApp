package com.example.wappdrop.Adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class CustomRecycler extends RecyclerView {

    private Context context;
    private int columnCount;

    public CustomRecycler(@NonNull Context context) {
        super(context);
        setUp(context);
    }

    public CustomRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUp(context);

    }

    public CustomRecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp(context);
    }
    /*
                context will casted ,it may help us whenever
                 */
    private void setUp(Context context){
        if (!isInEditMode()){
            this.context=context;
            notifyColumnCountChanged();
            
        }

    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        notifyColumnCountChanged();
    }

    private void notifyColumnCountChanged() {
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            this.columnCount=columnCount;
        }else {
            this.columnCount=2*columnCount;
        }
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null) {
            ((GridLayoutManager) layoutManager).setSpanCount(2);
        }
    }


    interface RecyclerInterface{
        void showFab();
        void hideFab();
        void loadRecyclerData(int page);
    }
}

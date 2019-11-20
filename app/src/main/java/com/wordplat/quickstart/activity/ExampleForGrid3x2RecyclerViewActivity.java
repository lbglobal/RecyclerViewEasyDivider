package com.wordplat.quickstart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wordplat.easydivider.RecyclerViewGridDivider;
import com.wordplat.quickstart.R;
import com.wordplat.quickstart.adapter.TextAdapter3;
import com.wordplat.quickstart.utils.AppUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by afon on 2017/3/4.
 */

@ContentView(R.layout.activity_grid3x2)
public class ExampleForGrid3x2RecyclerViewActivity extends BaseActivity {

    @ViewInject(R.id.gridList1) private RecyclerView gridList1 = null;
    @ViewInject(R.id.gridList2) private RecyclerView gridList2 = null;
    @ViewInject(R.id.gridList3) private RecyclerView gridList3 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI1();
        initUI2();
        initUI3();
    }

    private void initUI1() {
        TextAdapter3 textAdapter = new TextAdapter3(mActivity, 4);
        gridList1.setLayoutManager(new GridLayoutManager(mActivity, 3));
        gridList1.setAdapter(textAdapter);

        RecyclerViewGridDivider recyclerViewGridDivider = new RecyclerViewGridDivider(3, AppUtils.dpTopx(mActivity, 10), AppUtils.dpTopx(mActivity, 10));
        recyclerViewGridDivider.setDividerColor(0x88000000);
        recyclerViewGridDivider.setDividerSize(2);
        recyclerViewGridDivider.setDividerClipToPadding(false);
        gridList1.addItemDecoration(recyclerViewGridDivider);
    }

    private void initUI2() {
        TextAdapter3 textAdapter = new TextAdapter3(mActivity, 6);
        gridList2.setLayoutManager(new GridLayoutManager(mActivity, 3));
        gridList2.setAdapter(textAdapter);

        RecyclerViewGridDivider recyclerViewGridDivider = new RecyclerViewGridDivider(3, AppUtils.dpTopx(mActivity, 10), AppUtils.dpTopx(mActivity, 10));
        recyclerViewGridDivider.setRowDividerMargin(60, 60);
        recyclerViewGridDivider.setColDividerMargin(30, 30);
        recyclerViewGridDivider.setDividerColor(0x88000000);
        recyclerViewGridDivider.setDividerSize(2);
        recyclerViewGridDivider.setDividerClipToPadding(false);
        recyclerViewGridDivider.setShowLeftDivider(false);
        recyclerViewGridDivider.setShowRightDivider(false);
        recyclerViewGridDivider.setShowTopDivider(false);
        recyclerViewGridDivider.setShowBottomDivider(false);
        recyclerViewGridDivider.setFillItemDivider(true);
        gridList2.addItemDecoration(recyclerViewGridDivider);
    }

    private void initUI3() {
        TextAdapter3 textAdapter = new TextAdapter3(mActivity, 6);
        gridList3.setLayoutManager(new GridLayoutManager(mActivity, 3));
        gridList3.setAdapter(textAdapter);

        RecyclerViewGridDivider recyclerViewGridDivider = new RecyclerViewGridDivider(3, AppUtils.dpTopx(mActivity, 10), AppUtils.dpTopx(mActivity, 10));
        recyclerViewGridDivider.setRowDividerMargin(30, 30);
        recyclerViewGridDivider.setDividerColor(0x88000000);
        recyclerViewGridDivider.setDividerSize(2);
        recyclerViewGridDivider.setShowLeftDivider(false);
        recyclerViewGridDivider.setShowRightDivider(false);
        recyclerViewGridDivider.setFillItemDivider(true);
        gridList3.addItemDecoration(recyclerViewGridDivider);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ExampleForGrid3x2RecyclerViewActivity.class);
        return intent;
    }
}

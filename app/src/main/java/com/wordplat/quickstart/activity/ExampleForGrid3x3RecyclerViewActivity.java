package com.wordplat.quickstart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.wordplat.easydivider.RecyclerViewCornerRadius;
import com.wordplat.easydivider.RecyclerViewGridDivider;
import com.wordplat.quickstart.R;
import com.wordplat.quickstart.adapter.Grid3x3Adapter;
import com.wordplat.quickstart.utils.AppUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by afon on 2017/2/13.
 */

@ContentView(R.layout.activity_grid3x3)
public class ExampleForGrid3x3RecyclerViewActivity extends BaseActivity {

    @ViewInject(R.id.grid3x3List) private RecyclerView grid3x3List = null;

    private Grid3x3Adapter grid3x3Adapter;
    private RecyclerViewGridDivider recyclerViewGridDivider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 九宫格列表
        grid3x3Adapter = new Grid3x3Adapter(mActivity);
        grid3x3List.setLayoutManager(new GridLayoutManager(mActivity, 3));
        grid3x3List.setAdapter(grid3x3Adapter);

        RecyclerViewCornerRadius cornerRadius = new RecyclerViewCornerRadius(grid3x3List);
        cornerRadius.setCornerRadius(AppUtils.dpTopx(mActivity, 10));
        // 圆角背景必须第一个添加
        grid3x3List.addItemDecoration(cornerRadius);

        // 九宫格列表添加分割线
        final int margin = AppUtils.dpTopx(mActivity, 5);
        final int size = AppUtils.dpTopx(mActivity, 10);
        recyclerViewGridDivider = new RecyclerViewGridDivider(3, size, size);
        recyclerViewGridDivider.setRowDividerMargin(margin * 2, margin * 2);
        recyclerViewGridDivider.setColDividerMargin(margin, margin);
        recyclerViewGridDivider.setDividerClipToPadding(false);
        recyclerViewGridDivider.setDividerSize(1);
        recyclerViewGridDivider.setDividerColor(0x88000000);
        recyclerViewGridDivider.setBackgroundColor(0xffffffff);

        grid3x3List.addItemDecoration(recyclerViewGridDivider);
    }

    @Event(value = {R.id.actionBut1, R.id.actionBut2, R.id.actionBut3, R.id.actionBut4,
            R.id.actionBut5, R.id.actionBut6, R.id.actionBut7}, type = View.OnClickListener.class)
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionBut1:
                recyclerViewGridDivider.setFillItemDivider(!recyclerViewGridDivider.isFillItemDivider());
                break;

            case R.id.actionBut2:
                recyclerViewGridDivider.setShowTopDivider(!recyclerViewGridDivider.isShowTopDivider());
                break;

            case R.id.actionBut3:
                recyclerViewGridDivider.setShowBottomDivider(!recyclerViewGridDivider.isShowBottomDivider());
                break;

            case R.id.actionBut4:
                recyclerViewGridDivider.setShowLeftDivider(!recyclerViewGridDivider.isShowLeftDivider());
                break;

            case R.id.actionBut5:
                recyclerViewGridDivider.setShowRightDivider(!recyclerViewGridDivider.isShowRightDivider());
                break;

            case R.id.actionBut6:
                if (recyclerViewGridDivider.getColDividerMarginTop() == 0) {
                    final int margin = AppUtils.dpTopx(mActivity, 5);
                    recyclerViewGridDivider.setRowDividerMargin(margin * 2, margin * 2);
                    recyclerViewGridDivider.setColDividerMargin(margin, margin);
                } else {
                    recyclerViewGridDivider.setRowDividerMargin(0, 0);
                    recyclerViewGridDivider.setColDividerMargin(0, 0);
                }
                break;

            case R.id.actionBut7:
                recyclerViewGridDivider.setDividerClipToPadding(!recyclerViewGridDivider.isDividerClipToPadding());
                break;

            default:
                break;
        }
        grid3x3List.invalidateItemDecorations();
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ExampleForGrid3x3RecyclerViewActivity.class);
        return intent;
    }
}

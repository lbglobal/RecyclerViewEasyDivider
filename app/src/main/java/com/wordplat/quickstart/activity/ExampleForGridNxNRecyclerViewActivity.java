package com.wordplat.quickstart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wordplat.easydivider.RecyclerViewGridDivider;
import com.wordplat.quickstart.R;
import com.wordplat.quickstart.adapter.GridNxNAdapter;
import com.wordplat.quickstart.utils.AppUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by afon on 2017/2/13.
 */

@ContentView(R.layout.activity_gridnxn)
public class ExampleForGridNxNRecyclerViewActivity extends BaseActivity {

    @ViewInject(R.id.gridNxNList) private RecyclerView gridNxNList = null;

    private GridNxNAdapter gridNx3Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gridNx3Adapter = new GridNxNAdapter(mActivity);
        gridNxNList.setLayoutManager(new GridLayoutManager(mActivity, 5));
        gridNxNList.setAdapter(gridNx3Adapter);

        RecyclerViewGridDivider recyclerViewGridDivider = new RecyclerViewGridDivider(5, AppUtils.dpTopx(mActivity, 10), AppUtils.dpTopx(mActivity, 10));
        recyclerViewGridDivider.setDividerSize(2);
        recyclerViewGridDivider.setShowLeftDivider(false);
        recyclerViewGridDivider.setShowRightDivider(false);
        recyclerViewGridDivider.setDividerColor(0xffff0000);
        recyclerViewGridDivider.setDividerClipToPadding(false);
        // 暂不能设置虚线 缺陷：不适合设置虚线，画虚线需要设置 layerType 为 software，滑动列表时性能低下
//        recyclerViewGridDivider.getDividerPaint().setPathEffect(new DashPathEffect(new float[] {4, 4}, 0));
//        recyclerViewGridDivider.getDividerPaint().setStyle(Paint.Style.STROKE);
//        gridNxNList.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        gridNxNList.addItemDecoration(recyclerViewGridDivider);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ExampleForGridNxNRecyclerViewActivity.class);
        return intent;
    }
}

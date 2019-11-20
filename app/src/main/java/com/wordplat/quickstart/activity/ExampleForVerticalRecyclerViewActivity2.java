package com.wordplat.quickstart.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wordplat.easydivider.RecyclerViewCornerRadius;
import com.wordplat.easydivider.RecyclerViewLinearDivider;
import com.wordplat.quickstart.R;
import com.wordplat.quickstart.adapter.TextAdapter2;
import com.wordplat.quickstart.utils.AppUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by afon on 2017/2/13.
 */

@ContentView(R.layout.activity_vertical)
public class ExampleForVerticalRecyclerViewActivity2 extends BaseActivity {

    @ViewInject(R.id.textList) private RecyclerView textList = null;

    private TextAdapter2 textAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textAdapter = new TextAdapter2(mActivity);
        textList.setLayoutManager(new LinearLayoutManager(mActivity));
        textList.setAdapter(textAdapter);

        RecyclerViewCornerRadius cornerRadius = new RecyclerViewCornerRadius(textList);
        cornerRadius.setCornerRadius(AppUtils.dpTopx(mActivity, 10));

        RecyclerViewLinearDivider linearDivider = new RecyclerViewLinearDivider(mActivity, LinearLayoutManager.VERTICAL);
        linearDivider.setDividerSize(2);
        linearDivider.setDividerColor(0xffaa0000);
        linearDivider.setDividerBackgroundColor(0xffffffff);
        linearDivider.setDividerMargin(AppUtils.dpTopx(mActivity, 10), AppUtils.dpTopx(mActivity, 55));

        linearDivider.setShowHeaderDivider(false);
        linearDivider.setShowFooterDivider(false);

        // 设置虚线 这里可以设置虚线，不需要设置 layerType 为 software
        linearDivider.getDividerPaint().setPathEffect(new DashPathEffect(new float[] {4, 4}, 0));
        linearDivider.getDividerPaint().setStyle(Paint.Style.STROKE);

        // 圆角背景必须第一个添加
        textList.addItemDecoration(cornerRadius);
        textList.addItemDecoration(linearDivider);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ExampleForVerticalRecyclerViewActivity2.class);
        return intent;
    }
}

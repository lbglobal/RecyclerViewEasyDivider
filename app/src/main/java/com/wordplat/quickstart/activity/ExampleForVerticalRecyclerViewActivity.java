package com.wordplat.quickstart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
public class ExampleForVerticalRecyclerViewActivity extends BaseActivity {

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
        linearDivider.setDividerSize(AppUtils.dpTopx(mActivity, 10));
        linearDivider.setDividerColor(0xff888888);

        // 圆角背景必须第一个添加
        textList.addItemDecoration(cornerRadius);
        textList.addItemDecoration(linearDivider);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ExampleForVerticalRecyclerViewActivity.class);
        return intent;
    }
}

package com.wordplat.quickstart.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wordplat.easydivider.RecyclerViewLinearDivider;
import com.wordplat.quickstart.R;
import com.wordplat.quickstart.adapter.HorizontalAdapter;
import com.wordplat.quickstart.adapter.HorizontalAdapter2;
import com.wordplat.quickstart.utils.AppUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by afon on 2017/2/13.
 */

@ContentView(R.layout.activity_horizontal)
public class ExampleForHorizontalRecyclerViewActivity extends BaseActivity {

    @ViewInject(R.id.horizontalList) private RecyclerView horizontalList = null;
    @ViewInject(R.id.horizontalList2) private RecyclerView horizontalList2 = null;

    private HorizontalAdapter horizontalAdapter;
    private HorizontalAdapter2 horizontalAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        horizontalAdapter = new HorizontalAdapter(mActivity);
        horizontalList.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        horizontalList.setAdapter(horizontalAdapter);
        // 添加水平分割线
        RecyclerViewLinearDivider recyclerViewLinearDivider = new RecyclerViewLinearDivider(mActivity, LinearLayoutManager.HORIZONTAL, AppUtils.dpTopx(mActivity, 10), Color.parseColor("#eeeeee"));

        horizontalList.addItemDecoration(recyclerViewLinearDivider);

        horizontalAdapter2 = new HorizontalAdapter2(mActivity);
        horizontalList2.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        horizontalList2.setAdapter(horizontalAdapter2);
        // 添加水平分割线
        horizontalList2.addItemDecoration(recyclerViewLinearDivider);
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, ExampleForHorizontalRecyclerViewActivity.class);
        return intent;
    }
}

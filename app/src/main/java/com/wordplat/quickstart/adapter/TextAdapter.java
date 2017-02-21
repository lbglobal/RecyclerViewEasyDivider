package com.wordplat.quickstart.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordplat.quickstart.R;
import com.wordplat.quickstart.activity.ExampleForGrid3x3RecyclerViewActivity;
import com.wordplat.quickstart.activity.ExampleForGridNxNRecyclerViewActivity;
import com.wordplat.quickstart.activity.ExampleForHorizontalRecyclerViewActivity;
import com.wordplat.quickstart.activity.ExampleForVerticalRecyclerViewActivity;
import com.wordplat.quickstart.activity.ExampleForVerticalRecyclerViewActivity2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by afon on 2017/2/10.
 */

public class TextAdapter extends RecyclerView.Adapter<TextViewHolder> {

    private static List<String> textList = new ArrayList<>();

    static {
        textList.add("ExampleForVerticalRecyclerView");
        textList.add("ExampleForVerticalRecyclerView2");
        textList.add("ExampleForHorizontalRecyclerView");
        textList.add("ExampleForGrid3x3RecyclerView");
        textList.add("ExampleForGridNxNRecyclerView");
    }

    private Activity mActivity;

    public TextAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_text, parent, false);

        return new TextViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, final int position) {
        holder.text.setText(textList.get(position));
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = ExampleForVerticalRecyclerViewActivity.createIntent(mActivity);
                        break;

                    case 1:
                        intent = ExampleForVerticalRecyclerViewActivity2.createIntent(mActivity);
                        break;

                    case 2:
                        intent = ExampleForHorizontalRecyclerViewActivity.createIntent(mActivity);
                        break;

                    case 3:
                        intent = ExampleForGrid3x3RecyclerViewActivity.createIntent(mActivity);
                        break;

                    case 4:
                        intent = ExampleForGridNxNRecyclerViewActivity.createIntent(mActivity);
                        break;

                    default:
                        break;
                }

                if (intent != null) {
                    mActivity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return textList.size();
    }
}

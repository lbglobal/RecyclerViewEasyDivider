package com.wordplat.quickstart.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordplat.quickstart.R;

/**
 * Created by afon on 2017/2/13.
 */

public class TextAdapter2 extends RecyclerView.Adapter<TextViewHolder> {

    private Activity mActivity;

    public TextAdapter2(Activity activity) {
        mActivity = activity;
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_text, parent, false);

        return new TextViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        holder.text.setText("position " + position);
    }

    @Override
    public int getItemCount() {
        return 500;
    }
}

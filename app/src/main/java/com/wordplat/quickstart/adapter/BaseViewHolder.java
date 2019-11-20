package com.wordplat.quickstart.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import org.xutils.x;

/**
 * Created by afon on 2017/2/5.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    protected Context mContext;

    public BaseViewHolder(View itemView) {
        super(itemView);

        mContext = itemView.getContext();

        x.view().inject(this, itemView);
    }
}

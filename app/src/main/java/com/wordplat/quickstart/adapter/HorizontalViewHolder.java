package com.wordplat.quickstart.adapter;

import android.view.View;
import android.widget.ImageView;

import com.wordplat.quickstart.R;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by afon on 2017/2/5.
 */

public class HorizontalViewHolder extends BaseViewHolder {

    @ViewInject(R.id.image) ImageView image;

    public HorizontalViewHolder(View itemView) {
        super(itemView);
    }
}

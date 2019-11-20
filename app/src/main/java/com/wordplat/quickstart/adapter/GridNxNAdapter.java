package com.wordplat.quickstart.adapter;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wordplat.quickstart.R;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by afon on 2017/2/5.
 */

public class GridNxNAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private static List<String> imageList = new ArrayList<>();

    static {
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=TrakaiIslandCastle_EN-US13260881447_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=RossFountain_EN-US11490955168_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=EifelNPBelgium_EN-US13320978952_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=NASAEgypt_EN-US11074181873_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=TempleOfValadier_EN-US13731018326_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=MacaquesWulingyuan_EN-US8705472129_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=KongdeRi_EN-US11829528696_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=GreatCourt_EN-US11131065922_1366x768.jpg&w=300");
        imageList.add("http://www.istartedsomething.com/bingimages/resize.php?i=YerbaBuenaGardens_EN-US14307470964_1366x768.jpg&w=300");
    }

    private Activity mActivity;

    public GridNxNAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_image_grid, parent, false);

        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Picasso.with(mActivity)
                .load(imageList.get(position % imageList.size()))
                .placeholder(R.mipmap.image_placeholder)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return 270;
    }
}

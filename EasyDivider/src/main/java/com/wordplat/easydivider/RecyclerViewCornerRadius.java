package com.wordplat.easydivider;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import static android.view.View.LAYER_TYPE_SOFTWARE;

/**
 * 圆角 RecyclerView
 * <p>
 * Created by afon on 2017/1/5.
 */

public class RecyclerViewCornerRadius extends RecyclerView.ItemDecoration {
    public static final String TAG = "CornerRadius";

    private final RectF rectF;
    private final Path path;

    private int topLeftRadius = 0;
    private int topRightRadius = 0;
    private int bottomLeftRadius = 0;
    private int bottomRightRadius = 0;

    private View lastItemView;
    private int recyclerViewBottom = -1;

    public RecyclerViewCornerRadius(final RecyclerView recyclerView) {
        path = new Path();
        rectF = new RectF();
        rectF.set(0, 0, 0, 0);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            recyclerView.setLayerType(LAYER_TYPE_SOFTWARE, null);
        }

        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rectF.set(0, 0, recyclerView.getMeasuredWidth(), recyclerView.getMeasuredHeight());

                setRoundRect();
            }
        });
    }

    public void setCornerRadius(int radius) {
        this.topLeftRadius = radius;
        this.topRightRadius = radius;
        this.bottomLeftRadius = radius;
        this.bottomRightRadius = radius;
    }

    public void setCornerRadius(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius) {
        this.topLeftRadius = topLeftRadius;
        this.topRightRadius = topRightRadius;
        this.bottomLeftRadius = bottomLeftRadius;
        this.bottomRightRadius = bottomRightRadius;
    }

    private void setRoundRect() {
        path.reset();
        path.addRoundRect(rectF, new float[]{
                topLeftRadius, topLeftRadius,
                topRightRadius, topRightRadius,
                bottomLeftRadius, bottomLeftRadius,
                bottomRightRadius, bottomRightRadius
        }, Path.Direction.CCW);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int childCount = parent.getAdapter().getItemCount();

        if (parent.getChildAdapterPosition(view) == childCount - 1) {
            lastItemView = view;
        } else {
            lastItemView = null;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (lastItemView != null) {
            recyclerViewBottom = lastItemView.getBottom() + parent.getLayoutManager().getBottomDecorationHeight(lastItemView);

            if (rectF.bottom > recyclerViewBottom) {
                rectF.bottom = recyclerViewBottom;

                setRoundRect();
            }
        }

        c.clipRect(rectF);
        c.clipPath(path, Region.Op.REPLACE);
    }
}

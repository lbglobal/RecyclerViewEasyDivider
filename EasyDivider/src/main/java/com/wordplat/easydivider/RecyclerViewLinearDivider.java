package com.wordplat.easydivider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 适用于线性布局的 RecyclerView 分割线
 *
 * Created by afon on 2017/1/4.
 */

public class RecyclerViewLinearDivider extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Paint dividerPaint; // 分割线
    private Paint dividerBackgroundPaint; // 分割线背景，作用是当设置了分割线 marginStart 或 marginEnd 后，空出来的那部分区域也画上
    private Drawable divider;
    private int dividerHeight = 2; // 分割线高度，默认为 1px
    private int orientation; // RecyclerView 的方向：LinearLayoutManager.VERTICAL 或 LinearLayoutManager.HORIZONTAL，绘制分割线时也用这个方向
    private boolean showHeaderDivider = true; // 是否显示头部分割线
    private boolean showFooterDivider = true; // 是否显示尾部分割线
    private boolean dividerClipToPadding = true; // 如果 RecyclerView 设置了 padding，是否把分割线画在 padding 之内
    private int dividerMarginStart = 0; // 分割线 marginStart
    private int dividerMarginEnd = 0; // 分割线 marginEnd

    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context
     * @param orientation RecyclerView 列表方向
     */
    public RecyclerViewLinearDivider(Context context, int orientation) {
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("wrong args");
        }
        this.orientation = orientation;

        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        divider = a.getDrawable(0);
        a.recycle();
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation RecyclerView 列表方向
     * @param drawableId  分割线图片
     */
    public RecyclerViewLinearDivider(Context context, int orientation, int drawableId) {
        this(context, orientation);
        divider = ContextCompat.getDrawable(context, drawableId);
        dividerHeight = divider.getIntrinsicHeight();
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation RecyclerView 列表方向
     * @param divider 分割线
     */
    public RecyclerViewLinearDivider(Context context, int orientation, Drawable divider) {
        this(context, orientation);
        this.divider = divider;
        dividerHeight = divider.getIntrinsicHeight();
    }

    /**
     * 自定义分割线
     *
     * @param context
     * @param orientation   RecyclerView 列表方向
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public RecyclerViewLinearDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
        this(context, orientation);
        this.dividerHeight = dividerHeight;
        setDividerColor(dividerColor);
    }

    private void initDivider() {
        if (dividerPaint == null) {
            dividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            dividerPaint.setStyle(Paint.Style.FILL);
        }
        if (dividerBackgroundPaint == null) {
            dividerBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            dividerBackgroundPaint.setStyle(Paint.Style.FILL);
        }
    }

    /**
     * 设置分割线画笔
     */
    public void setDividerPaint(Paint dividerPaint) {
        this.dividerPaint = dividerPaint;
    }

    /**
     * 获取分割线画笔
     */
    public Paint getDividerPaint() {
        return dividerPaint;
    }

    /**
     * 设置分割线大小
     */
    public void setDividerSize(int dividerSize) {
        initDivider();
        this.dividerHeight = dividerSize;
        dividerPaint.setStrokeWidth(dividerSize);
        dividerBackgroundPaint.setStrokeWidth(dividerSize);
    }

    /**
     * 设置分割线颜色
     */
    public void setDividerColor(int dividerColor) {
        initDivider();
        dividerPaint.setColor(dividerColor);
    }

    /**
     * 设置分割线背景颜色
     */
    public void setDividerBackgroundColor(int dividerBackgroundColor) {
        initDivider();
        dividerBackgroundPaint.setColor(dividerBackgroundColor);
    }

    /**
     * 设置是否显示头部分割线
     */
    public void setShowHeaderDivider(boolean showHeaderDivider) {
        this.showHeaderDivider = showHeaderDivider;
    }

    /**
     * 设置是否显示尾部分割线
     */
    public void setShowFooterDivider(boolean showFooterDivider) {
        this.showFooterDivider = showFooterDivider;
    }

    /**
     * 设置是否把分割线画在 RecyclerView 的 padding 之内
     */
    public void setDividerClipToPadding(boolean dividerClipToPadding) {
        this.dividerClipToPadding = dividerClipToPadding;
    }

    /**
     * 设置分割线 margin
     *
     * @param dividerMarginStart 分割线 marignStart
     * @param dividerMarginEnd 分割线 marginEnd
     */
    public void setDividerMargin(int dividerMarginStart, int dividerMarginEnd) {
        this.dividerMarginStart = dividerMarginStart;
        this.dividerMarginEnd = dividerMarginEnd;
    }

    /**
     * 获取分割线尺寸
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int headerDividerHeight = showHeaderDivider ? dividerHeight : 0;
        int footerDividerHeight = showFooterDivider ? dividerHeight : 0;
        int childCount = parent.getAdapter().getItemCount();

        if (parent.getChildAdapterPosition(view) == 0) {
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.set(0, headerDividerHeight, 0, dividerHeight);
            } else {
                outRect.set(headerDividerHeight, 0, dividerHeight, 0);
            }
        } else if (parent.getChildAdapterPosition(view) == childCount - 1) {
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.set(0, 0, 0, footerDividerHeight);
            } else {
                outRect.set(0, 0, footerDividerHeight, 0);
            }
        } else {
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.set(0, 0, 0, dividerHeight);
            } else {
                outRect.set(0, 0, dividerHeight, 0);
            }
        }
    }

    /**
     * 绘制分割线
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    /**
     * 绘制横向 RecyclerView 的分割线
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = dividerClipToPadding ? parent.getPaddingTop() : 0;
        final int bottom = parent.getMeasuredHeight() - (dividerClipToPadding ? parent.getPaddingBottom() : 0);
        final int childSize = parent.getChildCount();

        // i 从 -1 开始是为了画 headerDivider
        for (int i = showHeaderDivider ? -1 : 0; i < childSize; i++) {
            final int left = i == -1 ? parent.getChildAt(0).getLeft() - dividerHeight : parent.getChildAt(i).getRight();
            final int right = left + dividerHeight;

            if (divider != null) {
                divider.setBounds(left, top + dividerMarginStart, right, bottom - dividerMarginEnd);
                divider.draw(canvas);
            }

            if (dividerBackgroundPaint != null) {

                if (dividerMarginStart > 0 || dividerMarginEnd > 0) {
                    canvas.drawRect(left, top, right, bottom, dividerBackgroundPaint);
                }
            }

            if (dividerPaint != null) {
                canvas.drawRect(left, top + dividerMarginStart, right, bottom - dividerMarginEnd, dividerPaint);
            }
        }
    }

    /**
     * 绘制纵向 RecyclerView 的分割线
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = dividerClipToPadding ? parent.getPaddingLeft() : 0;
        final int right = parent.getMeasuredWidth() - (dividerClipToPadding ? parent.getPaddingRight() : 0);
        final int childSize = parent.getChildCount();

        // i 从 -1 开始是为了画 headerDivider
        for (int i = showHeaderDivider ? -1 : 0; i < childSize; i++) {
            final int top = i == -1 ? parent.getChildAt(0).getTop() - dividerHeight : parent.getChildAt(i).getBottom();
            final int bottom = top + dividerHeight;

            if (divider != null) {
                divider.setBounds(left + dividerMarginStart, top, right - dividerMarginEnd, bottom);
                divider.draw(canvas);
            }

            if (dividerBackgroundPaint != null) {

                if (dividerMarginStart > 0 || dividerMarginEnd > 0) {
                    canvas.drawRect(left, top, right, bottom, dividerBackgroundPaint);
                }
            }

            if (dividerPaint != null) {
                canvas.drawRect(left + dividerMarginStart, top, right - dividerMarginEnd, bottom, dividerPaint);
            }
        }
    }

    public int getDividerHeight() {
        return dividerHeight;
    }

    public boolean isShowHeaderDivider() {
        return showHeaderDivider;
    }

    public boolean isShowFooterDivider() {
        return showFooterDivider;
    }

    public boolean isDividerClipToPadding() {
        return dividerClipToPadding;
    }

    public int getDividerMarginStart() {
        return dividerMarginStart;
    }

    public int getDividerMarginEnd() {
        return dividerMarginEnd;
    }
}

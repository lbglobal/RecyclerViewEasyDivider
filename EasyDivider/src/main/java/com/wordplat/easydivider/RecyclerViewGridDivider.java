package com.wordplat.easydivider;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * 适用于垂直滚动网格布局的 RecyclerView 分割线
 * 注意：不适用于水平滚动网格布局
 * 缺陷：不适合设置虚线，画虚线需要设置 layerType 为 software，滑动列表时性能低下
 *
 * Created by afon on 2017/2/9.
 */

public class RecyclerViewGridDivider extends RecyclerView.ItemDecoration {
    public static final String TAG = "RecyclerViewGridDivider";

    private Paint backgroundPaint; // 背景画笔
    private Paint dividerPaint; // 分割线画笔

    private final int colCount; // 列数量
    private final int colSeparateSize; // item 水平间距
    private final int rowSeparateSize; // item 垂直间距

    private int backgroundColor = 0x00000000; // 背景颜色
    private int dividerColor = 0xffdddddd; // 分割线颜色
    private int dividerSize = 0; // 分割线大小
    private int colDividerMarginTop = 0; // 水平分割线 marignTop
    private int colDividerMarginBottom = 0; // 水平分割线 marginBottom
    private int rowDividerMarginLeft = 0; // 垂直分割线 marginLeft
    private int rowDividerMarginRight = 0; // 垂直分割线 marginRight

    private boolean dividerClipToPadding = true; // 如果 RecyclerView 设置了 padding，是否把分割线画在 padding 之内
    private boolean fillItemDivider = false; // 是否填充每个分割线与分割线之间的 margin
    private boolean showTopDivider = true; // show top divider of this RecyclerView
    private boolean showBottomDivider = true; // show bottom divider of this RecyclerView
    private boolean showLeftDivider = true; // show left divider of this RecyclerView
    private boolean showRightDivider = true; // show right divider of this RecyclerView

    private float[] lineBuffers = new float[4]; // 缓存的分割线坐标点，每四个一组，分别代表 (x0, y0), (x1, y1)
    private int[] insetBuffers = new int[2]; // 缓存的左右间距，每两个一组，分别代表 left, right 间距。用于 getItemOffsets 方法内设置左右间距
    private final int dividerOffsetX; // 每个 item 之间的分割线偏移量
    private final int dividerOffsetY; // 每个 item 之间的分割线偏移量

    /**
     * 自定义分割线
     * 注意：如果没有设置分割线大小(setDividerSize)，默认不画分割线，只有 item 之间的间距
     *
     * @param colCount 列数量
     * @param colSeparateSize item 水平间距
     * @param rowSeparateSize item 垂直间距
     */
    public RecyclerViewGridDivider(int colCount, int colSeparateSize, int rowSeparateSize) {
        if (colCount <= 1) {
            throw new IllegalArgumentException("wrong args! colCount must larger than 1");
        }
        this.colCount = colCount;
        this.colSeparateSize = colSeparateSize;
        this.rowSeparateSize = rowSeparateSize;

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.FILL);

        dividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dividerPaint.setColor(dividerColor);
        dividerPaint.setStrokeWidth(dividerSize);
        dividerPaint.setStyle(Paint.Style.FILL);

        dividerOffsetX = colSeparateSize / 2;
        dividerOffsetY = rowSeparateSize / 2;

        // 预分配 getItemOffsets 方法内设置的 left, right 间距。如果不这样计算，水平方向上的各个 item 的宽度将不相等
        float inset = colSeparateSize / colCount;
        insetBuffers = new int[colCount * 2];
        for (int i = 0 ; i < colCount ; i++) {
            // 计算规则：每个 item 的 left + right 要相等，前一个 item 的 right 加上后一个 item 的 left 等于 colSeparateSize
            insetBuffers[i * 2 + 0] = (int) (inset * i); // left
            insetBuffers[i * 2 + 1] = (int) (inset * (colCount - 1 - i)); // right
        }
    }

    /**
     * 设置背景画笔
     */
    public void setBackgroundPaint(Paint backgroundPaint) {
        this.backgroundPaint = backgroundPaint;
    }

    /**
     * 获取背景画笔
     */
    public Paint getBackgroundPaint() {
        return backgroundPaint;
    }

    /**
     * 设置背景颜色
     */
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        backgroundPaint.setColor(backgroundColor);
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
        this.dividerSize = dividerSize;
        dividerPaint.setStrokeWidth(dividerSize);
    }

    /**
     * 设置分割线颜色
     */
    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        dividerPaint.setColor(dividerColor);
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
     * @param colDividerMarginTop 水平分割线 marignTop
     * @param colDividerMarginBottom 水平分割线 marginBottom
     */
    public void setColDividerMargin(int colDividerMarginTop, int colDividerMarginBottom) {
        this.colDividerMarginTop = colDividerMarginTop;
        this.colDividerMarginBottom = colDividerMarginBottom;
    }

    /**
     * 设置分割线 margin
     *
     * @param rowDividerMarginLeft 垂直分割线 marginLeft
     * @param rowDividerMarginRight 垂直分割线 marginRight
     */
    public void setRowDividerMargin(int rowDividerMarginLeft, int rowDividerMarginRight) {
        this.rowDividerMarginLeft = rowDividerMarginLeft;
        this.rowDividerMarginRight = rowDividerMarginRight;
    }

    /**
     * 是否填充每个分割线与分割线之间的 margin
     */
    public void setFillItemDivider(boolean fillItemDivider) {
        this.fillItemDivider = fillItemDivider;
    }

    public void setShowTopDivider(boolean showTopDivider) {
        this.showTopDivider = showTopDivider;
    }

    public void setShowBottomDivider(boolean showBottomDivider) {
        this.showBottomDivider = showBottomDivider;
    }

    public void setShowLeftDivider(boolean showLeftDivider) {
        this.showLeftDivider = showLeftDivider;
    }

    public void setShowRightDivider(boolean showRightDivider) {
        this.showRightDivider = showRightDivider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        final int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();

        if (itemPosition < colCount) {
            outRect.top = 0;
        } else {
            outRect.top = rowSeparateSize;
        }

        outRect.bottom = 0;

        int i = itemPosition % colCount;
        outRect.left = insetBuffers[i * 2 + 0];
        outRect.right = insetBuffers[i * 2 + 1];
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        final int paddingLeft = parent.getPaddingLeft();
        final int paddingTop = parent.getPaddingTop();
        final int paddingRight = parent.getPaddingRight();
        final int paddingBottom = parent.getPaddingBottom();

        c.drawRect(parent.getLeft() - paddingLeft,
                parent.getTop() - paddingTop,
                parent.getRight() + paddingRight,
                parent.getBottom() + paddingBottom, backgroundPaint);

        // 如果分割线的大小没有设置，默认不画分割线，只有 item 之间的间距
        if (dividerSize <= 0) {
            return;
        }

        final int itemCount = parent.getChildCount(); // 当前 RecyclerView 的子 view 的数量
        final int rowCount = (int) Math.ceil( (double) itemCount / colCount); // 行数量
        final int fullLineItemCount = colCount * rowCount; // 填满一整行的 RecyclerView 子 view 数量
        final int lastRowStart = fullLineItemCount - colCount; // 最后一行开始的位置
        final int lineCount = (colCount + 1) * rowCount + colCount * (rowCount + 1); // 需要绘制的分割线数量

        // 用这种方式避免频繁申请内存
        if (lineBuffers.length < lineCount * 4) {
            lineBuffers = new float[lineCount * 4];
        }

        int lineOffset = 0;

        for (int i = 0 ; i < itemCount ; i++) {
            final boolean isFirstCol = i % colCount == 0; // 是否第一列
            final boolean isLastCol = (i + 1) % colCount == 0; // 是否最后一列
            final boolean isFirstRow = i < colCount; // 是否第一行
            final boolean isLastRow = i >= lastRowStart; // 是否最后一行

            final boolean isLeftTopCorner = isFirstCol && isFirstRow; // 是否左上角
            final boolean isRightTopCorner = isLastCol && isFirstRow; // 是否右上角
            final boolean isLeftBottomCorner = isFirstCol && isLastRow; // 是否左下角
            final boolean isRightBottomCorner = isLastCol && isLastRow; // 是否左下角

            final View itemView = parent.getChildAt(i);
            final int top = itemView.getTop() - dividerOffsetY;
            final int bottom = itemView.getBottom() + dividerOffsetY;
            final int left = itemView.getLeft() - dividerOffsetX;
            final int right = itemView.getRight() + dividerOffsetX;

            final int clipTop = !dividerClipToPadding && !showTopDivider && isFirstRow ? top - paddingTop : top;
            final int clipBottom = !dividerClipToPadding && !showBottomDivider && isLastRow ? bottom + paddingBottom : bottom;
            final int clipLeft = !dividerClipToPadding && !showLeftDivider && isFirstCol ? left - paddingLeft : left;
            final int clipRight = !dividerClipToPadding && !showRightDivider && isLastCol ? right + paddingRight : right;

            if (showLeftDivider && isFirstCol) {
                final int offsetTop = fillItemDivider && (!isFirstRow || isLeftTopCorner) ? 0 : colDividerMarginTop;
                final int offsetBottom = fillItemDivider && (!isLastRow || isLeftBottomCorner) ? 0 : colDividerMarginBottom;

                lineOffset = addLineBuffer(lineOffset, left, clipTop + offsetTop, left, clipBottom - offsetBottom); // the left side of this item
            }

            if (showTopDivider && isFirstRow) {
                final int offsetLeft = fillItemDivider && (!isFirstCol || isLeftTopCorner) ? 0 : rowDividerMarginLeft;
                final int offsetRight = fillItemDivider && (!isLastCol || isRightTopCorner) ? 0 : rowDividerMarginRight;

                lineOffset = addLineBuffer(lineOffset, clipLeft + offsetLeft, top, clipRight - offsetRight, top); // the top side of this item
            }

            if (showRightDivider && isLastCol || !isLastCol) {
                final int offsetTop = fillItemDivider && (!isFirstRow || isRightTopCorner) ? 0 : colDividerMarginTop;
                final int offsetBottom = fillItemDivider && (!isLastRow || isRightBottomCorner) ? 0 : colDividerMarginBottom;

                lineOffset = addLineBuffer(lineOffset, right, clipTop + offsetTop, right, clipBottom - offsetBottom); // the right side of this item
            }

            if (showBottomDivider && isLastRow || !isLastRow) {
                final int offsetLeft = fillItemDivider && (!isFirstCol || isLeftBottomCorner) ? 0 : rowDividerMarginLeft;
                final int offsetRight = fillItemDivider && (!isLastCol || isRightBottomCorner) ? 0 : rowDividerMarginRight;

                lineOffset = addLineBuffer(lineOffset, clipLeft + offsetLeft, bottom, clipRight - offsetRight, bottom); // the bottom side of this item
            }
        }

        // 使用 drawLines 方法比依次调用 drawLine 要快
        c.drawLines(lineBuffers, 0, lineOffset * 4, dividerPaint);
    }

    private int addLineBuffer(int lineOffset, int x0, int y0, int x1, int y1) {
        lineBuffers[lineOffset * 4 + 0] = x0; // x0
        lineBuffers[lineOffset * 4 + 1] = y0; // y0
        lineBuffers[lineOffset * 4 + 2] = x1; // x1
        lineBuffers[lineOffset * 4 + 3] = y1; // y1
        return ++lineOffset;
    }

    private void debug(int index, int i, String strArgs) {
        if (i == index) {
            Log.e(TAG, "##d debug: i = " + i + ", " + strArgs);
        }
    }

    public int getColCount() {
        return colCount;
    }

    public int getColSeparateSize() {
        return colSeparateSize;
    }

    public int getRowSeparateSize() {
        return rowSeparateSize;
    }

    public int getDividerSize() {
        return dividerSize;
    }

    public int getColDividerMarginTop() {
        return colDividerMarginTop;
    }

    public int getColDividerMarginBottom() {
        return colDividerMarginBottom;
    }

    public int getRowDividerMarginLeft() {
        return rowDividerMarginLeft;
    }

    public int getRowDividerMarginRight() {
        return rowDividerMarginRight;
    }

    public boolean isDividerClipToPadding() {
        return dividerClipToPadding;
    }

    public boolean isFillItemDivider() {
        return fillItemDivider;
    }

    public boolean isShowTopDivider() {
        return showTopDivider;
    }

    public boolean isShowBottomDivider() {
        return showBottomDivider;
    }

    public boolean isShowLeftDivider() {
        return showLeftDivider;
    }

    public boolean isShowRightDivider() {
        return showRightDivider;
    }
}
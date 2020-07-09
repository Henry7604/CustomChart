package com.henry.chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Henry
 */
public class ChartView extends View {
    private int mWidth;
    private int mHeight;
    private Paint mPaintLine;
    private Paint mPaintTransparent;
    private Paint mPaintCircle;

    private Context mContext;

    private List<ChartModel> mDataList;
    private TypedArray mTa;
    private int mGradientColorStart = 0;
    private int mGradientColorEnd = 0;

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            mTa = context.obtainStyledAttributes(attrs, com.henry.chart.R.styleable.ChartView);
        }
        mContext = context;
        mDataList = new ArrayList<>();

        // 折線
        mPaintLine =new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setStrokeWidth(Utility.convertDpToPixel(mContext, 1));
//        mPaintLine.setColor(ContextCompat.getColor(context, R.color.dark_lavender));

        // 前後空白
        mPaintTransparent =new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTransparent.setStrokeWidth(Utility.convertDpToPixel(mContext, 1));
        mPaintTransparent.setColor(mContext.getColor(android.R.color.transparent));

        // 小圓
        mPaintCircle =new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setColor(Color.WHITE);

        mWidth = Utility.convertDpToPixel(mContext, 45);
        mHeight = Utility.convertDpToPixel(mContext, 60);

        if (mTa != null) {
            mPaintLine.setColor(mTa.getColor(com.henry.chart.R.styleable.ChartView_paint_line_color, ContextCompat.getColor(mContext, android.R.color.transparent)));
            mGradientColorStart = mTa.getColor(com.henry.chart.R.styleable.ChartView_gradient_color_start, ContextCompat.getColor(mContext, android.R.color.transparent));
            mGradientColorEnd = mTa.getColor(com.henry.chart.R.styleable.ChartView_gradient_color_end, ContextCompat.getColor(mContext, android.R.color.transparent));
            mTa.recycle();
        } else {
            mGradientColorStart = ContextCompat.getColor(mContext, android.R.color.transparent);
            mGradientColorEnd = ContextCompat.getColor(mContext, android.R.color.transparent);
        }
        invalidate();
    }

    public void setItemWidth(int width) {
        mWidth = width;
    }

    public void setData(List<ChartModel> dataList) {
        if (mDataList != null && mDataList.size() > 0) {
            mDataList.clear();
        }
        mDataList.addAll(dataList);

        requestLayout();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mDataList != null && mDataList.size() > 0) {
            for (int i = 0; i < mDataList.size(); i++) {
                ChartModel previousData = mDataList.size() > 1 && i != 0 ? mDataList.get(i - 1) : null;
                ChartModel nowData = mDataList.get(i);
                ChartModel nextData = mDataList.size() > i + 1 ? mDataList.get(i + 1) : null;

                if (nowData.getData() == -999) {
                    canvas.drawLine(mWidth * i,0, (mWidth * i) + mWidth, 0, mPaintTransparent);
                } else {
                    int start = 120 - (nowData.getData() + 20);
                    float startY = mHeight - nowData.getData() > 0 ? (start / 120f) * mHeight : 0;

                    if (nextData != null && nextData.getData() != -999) {
                        int end = 120 - (nextData.getData() + 20);
                        float endY =  mHeight - nextData.getData() > 0 ? (end / 120f) * mHeight : 0;
                        // 畫折線
                        canvas.drawLine(mWidth * i, startY, (mWidth * i) + mWidth, endY, mPaintLine);

                        // 漸層色路徑
                        Path path = new Path();
                        path.moveTo(mWidth * i, mHeight);
                        path.lineTo(mWidth * i, startY);
                        path.lineTo((mWidth * i) + mWidth, endY);
                        path.lineTo((mWidth * i) + mWidth, mHeight);
                        path.close();
                        LinearGradient linearGradient = new LinearGradient(mWidth * i, 0, mWidth * i, mHeight, mGradientColorStart, mGradientColorEnd, Shader.TileMode.CLAMP);
                        Paint pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                        pathPaint.setShader(linearGradient);
                        canvas.drawPath(path, pathPaint);
                    }

                    // 畫小圓
                    canvas.drawCircle(mWidth * i, startY, Utility.convertDpToPixel(mContext, 2), mPaintCircle);
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mDataList.size() == 0) {
            setMeasuredDimension(mWidth, mHeight);
        } else {
            setMeasuredDimension(mWidth * mDataList.size(), mHeight);
        }
    }
}

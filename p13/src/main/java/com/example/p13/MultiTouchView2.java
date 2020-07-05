package com.example.p13;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.alpha.utils.PublicUtilsKt;

public class MultiTouchView2 extends View {


    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    final float ImageWidth = PublicUtilsKt.dp2px(200);
    Bitmap bitmap;
    float offsetX, offsetY;
    float downX, downY;
    float originalOffsetX, originalOffsetY;
    int trackingPointerId = 0;
    float focusX, focusY;

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        bitmap = Utils.getAvatar(getResources(), ImageWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(offsetX, offsetY);
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float sumX = 0, sumY = 0;
        int pointerCount = event.getPointerCount();
        boolean isPointerUp = event.getActionMasked() == MotionEvent.ACTION_POINTER_UP;

        for (int i = 0; i < pointerCount; i++) {
            if (!(isPointerUp && i == event.getActionIndex())) {
                sumX += event.getX(i);
                sumY += event.getY(i);
            }
        }

        if (isPointerUp) {
            pointerCount--;
        }

        focusX = sumX / pointerCount;
        focusY = sumY / pointerCount;

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP: {
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                downX = focusX;
                downY = focusY;
                invalidate();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                offsetX = originalOffsetX + sumX / pointerCount - downX;
                offsetY = originalOffsetY + sumY / pointerCount - downY;
                invalidate();
                break;
            }
        }


        return true;
    }
}

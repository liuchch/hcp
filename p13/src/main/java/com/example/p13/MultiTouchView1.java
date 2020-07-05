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

public class MultiTouchView1 extends View {


    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    final float ImageWidth = PublicUtilsKt.dp2px(200);
    Bitmap bitmap;
    float offsetX, offsetY;
    float downX, downY;
    float originalOffsetX, originalOffsetY;
    int trackingPointerId = 0;

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                trackingPointerId = event.getPointerId(0);
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(trackingPointerId);
                offsetX = originalOffsetX + event.getX(index) - downX;
                offsetY = originalOffsetY + event.getY(index) - downY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex = event.getActionIndex();
                trackingPointerId = event.getPointerId(actionIndex);
                downX = event.getX(actionIndex);
                downY = event.getY(actionIndex);
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                int id = event.getPointerId(actionIndex);
                if (id == trackingPointerId) {
                    int newIndex;
                    if (actionIndex != event.getPointerCount() - 1) {
                        newIndex = event.getPointerCount() - 1;
                    } else {
                        newIndex = event.getPointerCount() - 2;
                    }
                    trackingPointerId = event.findPointerIndex(newIndex);
                    index = event.findPointerIndex(trackingPointerId);
                    downX = event.getX(index);
                    downY = event.getY(index);
                    originalOffsetX = offsetX;
                    originalOffsetY = offsetY;
                break;
            }
            case MotionEvent.ACTION_UP:
                break;
        }


        return true;
    }
}

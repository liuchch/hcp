package com.alpha.p6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class PieView extends View {

    Paint paint;
    int[] colors = {
            Color.parseColor("#234cab"),
            Color.parseColor("#abc138"),
            Color.parseColor("#ca1100"),
            Color.parseColor("#1bc211")};
    int[] angles = {130,40,80, 110};
    int midX, midY;
    int radius = Utils.dp2px(150);


    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(Utils.dp2px(20f));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        midX = getWidth()/2;
        midY = getHeight()/2;

        int currentAngle = 0;
        int highlightIndex = 7;
        int deviateLength = Utils.dp2px(20);

        for (int i=0; i<colors.length; i++) {
            canvas.save();
            if (highlightIndex == i) {
                float deviateX = (float) (deviateLength * Math.cos(Math.toRadians(currentAngle + angles[i]/2)));
                float deviateY = (float) (deviateLength * Math.sin(Math.toRadians(currentAngle + angles[i]/2)));
                canvas.translate(deviateX, deviateY);
            }
            paint.setColor(colors[i]);
            canvas.drawArc(midX - radius, midY - radius,
                midX + radius, midY + radius,
                    currentAngle, angles[i], true, paint);

            currentAngle += angles[i];
            canvas.restore();
        }

    }
}

package com.alpha.p6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DashView extends View {


    Paint paint;

    int midX, midY;
    int radius;
    int startAngle = 120;
    int sweepAngle = 300;
    Path dash = new Path();
    PathEffect pathEffect;



    public DashView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        radius = Utils.dp2px(150);
        dash.addRect(0, 0,
                Utils.dp2px(2),
                Utils.dp2px(10), Path.Direction.CW);


        Path arc = new Path();
        arc.addArc(midX-radius,
                midY-radius,
                midX+radius,
                midY+radius,
                startAngle, sweepAngle);

        PathMeasure pathMeasure = new PathMeasure(arc, false);
        float advance = (pathMeasure.getLength() - Utils.dp2px(2))/20;

        pathEffect = new PathDashPathEffect(dash, advance, 0, PathDashPathEffect.Style.ROTATE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        midX = getWidth()/2;
        midY = getHeight()/2;

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(2));
        canvas.drawArc(midX-radius,
                midY-radius,
                midX+radius,
                midY+radius,
                startAngle, sweepAngle, false, paint
                );

        paint.setPathEffect(pathEffect);
        canvas.drawArc(midX-radius,
                midY-radius,
                midX+radius,
                midY+radius,
                startAngle, sweepAngle, false, paint
        );
        paint.setPathEffect(null);

        // 刻度7 角度为 90+30=120+240/20*7 =
        int scale = 7;
        int scaleAngle = 120 + 300/20*scale;
        int lineLength = Utils.dp2px(120);
        float endx = (float) (lineLength * Math.cos(Math.toRadians(scaleAngle)));
        float endy = (float) (lineLength * Math.sin(Math.toRadians(scaleAngle)));
        canvas.drawLine(midX, midY, endx + midX, endy + midY, paint);

    }




}

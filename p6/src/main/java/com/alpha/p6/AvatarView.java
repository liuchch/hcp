package com.alpha.p6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class AvatarView extends View {

    private int width = Utils.dp2px(300);
    private float padding = Utils.dp2px(50);

    Paint paint;

    int midX, midY;
    int radius = Utils.dp2px(150);


    Bitmap bitmap;
    RectF savedArea = new RectF();
    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);



    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = getAvatar(width);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        savedArea.set(0,0,width,width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(0,0, width+20, width+20, paint);
        canvas.translate(10,10);
        int saved = canvas.saveLayer(savedArea, paint);
        canvas.drawOval(0,0, width, width, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saved);
    }


    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
    }

}

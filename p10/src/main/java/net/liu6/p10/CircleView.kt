package net.liu6.p10

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.alpha.utils.dp2px

class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val PADDING = dp2px(30)
    val RADIUS = dp2px(80)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var width = (PADDING + RADIUS) * 2
        var height = (PADDING + RADIUS) * 2

        width = resolveSizeAndState(width.toInt(), widthMeasureSpec, 0).toFloat()
        height = resolveSizeAndState(height.toInt(), heightMeasureSpec, 0).toFloat()

        setMeasuredDimension(width.toInt(), height.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(Color.RED)

        paint.color = Color.BLUE
        canvas?.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, paint)

    }
}
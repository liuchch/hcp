package com.alpha.p7.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.alpha.p7.R
import com.alpha.p7.Utils

class SportsTextView(context: Context?, attrs: AttributeSet?)  : View(context, attrs){

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var midX = 0f
    var midY = 0f
    val radius = Utils.dp2px(100f)

    val rect = Rect()

    var fontMetrics = Paint.FontMetrics()
    init {
        paint.textSize = Utils.dp2px(30f).toFloat()
        paint.textAlign = Paint.Align.CENTER
        paint.getFontMetrics(fontMetrics)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        midX = width/2f
        midY = height/2f

        //draw gray ring
        paint.color = resources.getColor(R.color.gray)

        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = Utils.dp2px(20f).toFloat()


        //画灰色环
        canvas?.drawArc(
            midX - radius,
            midY - radius,
            midX + radius,
            midY + radius,
            0f, 360f, false, paint
            )


        //画红色进度
        paint.color = resources.getColor(R.color.red)
        canvas?.drawArc(
            midX - radius,
            midY - radius,
            midX + radius,
            midY + radius,
            -90f, 210f, false, paint
        )


        paint.style = Paint.Style.FILL
        val text = "ababppgg"
//        paint.getTextBounds(text, 0, text.length, rect)
//        val offset = (rect.top + rect.bottom)/2
        val offset = (fontMetrics.ascent + fontMetrics.descent) / 2
        canvas?.drawText(text, width/2.toFloat(), height/2.toFloat() - offset, paint)


    }
}
package net.liu6.p10

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import kotlin.math.max

class SquareImageView(context: Context?, attrs: AttributeSet?) : ImageView(context, attrs) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        val size = max(width, height)
        setMeasuredDimension(size, size)

    }
}
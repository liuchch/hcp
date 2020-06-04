package net.liu6.p10

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.max

class TagLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {


    var childLayoutList = ArrayList<Rect>()




    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var widthUsed = 0
        var heightUsed = 0

        var widthUsedLine = 0
        var maxLineUsedHeight = 0

        val widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec)

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            if (widthMeasureMode != MeasureSpec.UNSPECIFIED && child.measuredWidth + widthUsedLine > widthMeasureSize) {
                heightUsed += maxLineUsedHeight
                widthUsed = max(widthUsed, widthUsedLine)
                widthUsedLine = 0
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }
            maxLineUsedHeight = max(maxLineUsedHeight, child.measuredHeight)

            val rect = Rect(widthUsedLine, heightUsed, widthUsedLine + child.measuredWidth, heightUsed + child.measuredHeight)
            childLayoutList.add(rect)

            widthUsedLine += child.measuredWidth

            widthUsed = max(widthUsed, widthUsedLine)
        }

        heightUsed += maxLineUsedHeight

        setMeasuredDimension(widthUsed, heightUsed)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val rect = childLayoutList[i]
            child.layout(rect.left, rect.top, rect.right, rect.bottom)
//            child.layout(l, t, r, b)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}
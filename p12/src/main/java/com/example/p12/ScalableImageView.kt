package com.example.p12

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.alpha.utils.dp2px
import kotlin.math.max
import kotlin.math.min

class ScalableImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs), GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    val IMAGE_WIDTH = dp2px(300)
    var bitmap:Bitmap
    var offsetX = 0f
    var offsetY = 0f
    var bigScale = 1f
    var smallScale = 1f
    var fraction = 0f

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var gestureDetector : GestureDetector = GestureDetector(context, this)
    val isBig = false

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
        gestureDetector.setOnDoubleTapListener(this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        offsetX = (width - IMAGE_WIDTH) / 2
        offsetY = (height - IMAGE_WIDTH) / 2

        val widthScale = width / IMAGE_WIDTH
        val heightScale = height / IMAGE_WIDTH
        smallScale = min(widthScale, heightScale)
        bigScale = max(widthScale, heightScale)



    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.scale(smallScale, smallScale)
        canvas?.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        if (isBig) {

        }

        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }


}
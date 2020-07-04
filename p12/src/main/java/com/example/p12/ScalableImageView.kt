package com.example.p12

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import com.alpha.utils.dp2px
import kotlin.math.max
import kotlin.math.min

class ScalableImageView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val IMAGE_WIDTH = dp2px(300)
    var bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())

    var originalOffsetX = 0f
    var originalOffsetY = 0f
    var bigScale = 1f
    var smallScale = 1f
    var offsetX = 0f
    var offsetY = 0f

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var isBig = false
    var objectAnimator : ObjectAnimator? = null
    val overScale = 3
    var currentScale = 1f
        set(value) {
            field = value
            invalidate()
        }

    val scroller = OverScroller(context)
    val gestureListener = HenGestureListener()
    val gestureDetector = GestureDetectorCompat(context, gestureListener)
    val henScaleListener = HenScaleDetectorListener()
    val scaleGestureDetector = ScaleGestureDetector(context, henScaleListener)

    val runnable = object : Runnable {
        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                postOnAnimation(this)
            }
        }

    }

    fun getMyObjectAnimator() : ObjectAnimator {
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(this, "currentScale", smallScale, bigScale)
        }
        objectAnimator!!.setFloatValues(smallScale, bigScale)
        return objectAnimator!!
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        originalOffsetX = (width.toFloat() - bitmap.width) / 2
        originalOffsetY = (height.toFloat() - bitmap.height) / 2

        val widthScale = width / IMAGE_WIDTH
        val heightScale = height / IMAGE_WIDTH
        smallScale = min(widthScale, heightScale)
        bigScale = max(widthScale, heightScale) * 1.5f
        currentScale = smallScale
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var result = scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            result = gestureDetector.onTouchEvent(event)
        }
        return result
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas?.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        canvas?.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas?.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }


    inner class HenGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onShowPress(e: MotionEvent?) {

        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return false
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            if (isBig) {
                scroller.fling(
                    offsetX.toInt(),
                    offsetY.toInt(),
                    velocityX.toInt(),
                    velocityY.toInt(),
                    (-(IMAGE_WIDTH * currentScale - width) / 2).toInt(),
                    ((IMAGE_WIDTH * currentScale - width) / 2).toInt(),
                    (-(IMAGE_WIDTH * currentScale - height) / 2).toInt(),
                    ((IMAGE_WIDTH * currentScale - height) / 2).toInt()
                )
                postOnAnimation(runnable)
            }
            return false
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            if (isBig) {
                offsetX += -distanceX
                offsetY += -distanceY
                offsetX = max(offsetX, -(IMAGE_WIDTH * currentScale - width) / 2)
                offsetX = min(offsetX, (IMAGE_WIDTH * currentScale - width) / 2)
                offsetY = max(offsetY, -(IMAGE_WIDTH * currentScale - height) / 2)
                offsetY = min(offsetY, (IMAGE_WIDTH * currentScale - height) / 2)
                invalidate()
            }
            return false
        }


        override fun onLongPress(e: MotionEvent?) {

        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            if (isBig) {
                isBig = !isBig
                getMyObjectAnimator().reverse()
            } else {
                e?.let {
                    offsetX = (e.x - width / 2f) - (e.x - width / 2) * bigScale / smallScale
                    offsetY = (e.y - height / 2f) - (e.y - height / 2) * bigScale / smallScale
                    fixOffsets()
                }
                isBig = !isBig
                getMyObjectAnimator().start()
            }
            return false
        }

        override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
            return false
        }

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            return false
        }

    }

    private fun fixOffsets() {
        offsetX = Math.min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetX = Math.max(offsetX, -(bitmap.width * bigScale - width) / 2)
        offsetY = Math.min(offsetY, (bitmap.height * bigScale - height) / 2)
        offsetY = Math.max(offsetY, -(bitmap.height * bigScale - height) / 2)
    }

    inner class HenScaleDetectorListener : ScaleGestureDetector.OnScaleGestureListener {

        var initialScale = 1f

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            detector?.let {
                currentScale = initialScale * detector.scaleFactor
                invalidate()
            }
            return false
        }

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            initialScale = currentScale
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
        }
    }

}
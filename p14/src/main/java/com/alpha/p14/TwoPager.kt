package com.alpha.p14

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import kotlin.math.abs

class TwoPager : ViewGroup {

    var downX = 0f
    var downY = 0f
    var scrolling = false
    var downScrollX = 0f

    var viewConfiguration: ViewConfiguration = ViewConfiguration.get(context)
    private var velocityTracker = VelocityTracker.obtain()
    val maxVelocity = viewConfiguration.scaledMaximumFlingVelocity
    val minVelocity = viewConfiguration.scaledMinimumFlingVelocity
    var overScroller = OverScroller(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        var childTop = 0
        var childRight = width
        var childBottom = height

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.layout(childLeft, childTop, childRight, childBottom)
            childLeft += width
            childRight += width
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {

        var result = false
        ev?.let {

            if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
                velocityTracker.clear()
            }
            velocityTracker.addMovement(ev)

            when(ev.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    downX = ev.x
                    downY = ev.y
                    scrolling = false
                    downScrollX = scrollX.toFloat()
                }
                MotionEvent.ACTION_MOVE -> {
                    var dx = downX - ev.x
                    if (!scrolling
                        && abs(dx) > viewConfiguration.scaledPagingTouchSlop) {
                        scrolling = true
                        result = true
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
            }
        }


        return result
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event!!.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)

        event?.let {
            when(event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.x
                    downY = event.y
                    downScrollX = scrollX.toFloat()
                }
                MotionEvent.ACTION_MOVE -> {
                    var dx = downX - event.x + downScrollX
                    if (dx > width) {
                        dx = width.toFloat()
                    } else if (dx < 0) {
                        dx = 0f
                    }
                    scrollTo(dx.toInt(), 0)
                }
                MotionEvent.ACTION_UP -> {
                    velocityTracker.computeCurrentVelocity(1000, maxVelocity.toFloat())
                    val vx = velocityTracker.xVelocity
                    var targetPage = 0
                    if (abs(vx) < minVelocity) {
                        targetPage = if (scrollX > width / 2) 1 else 0
                    } else {
                        targetPage = if (vx < 0) 1 else 0
                    }
                    var  scrollDistance =
                        if (targetPage == 0)  - scrollX
                        else width - scrollX
                    overScroller.startScroll(scrollX, 0, scrollDistance, 0)
                    postInvalidateOnAnimation()
                }
            }

        }

        return true
    }

    override fun computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY)
            postInvalidateOnAnimation()
        }

    }
}
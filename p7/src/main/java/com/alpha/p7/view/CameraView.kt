package com.alpha.p7.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.alpha.p7.R
import com.alpha.p7.Utils
import com.alpha.p7.getZForCamera

class CameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var midX = 0
    var midY = 0

    var camera = Camera()

    val bitmap: Bitmap
    var bitmapWidth: Float

    init {
        paint.color = resources.getColor(R.color.red)



        camera.rotateX(30f)
        camera.setLocation(0f, 0f, getZForCamera(-4f))
        bitmapWidth = Utils.dp2px(200f)
        bitmap = getAvatar(bitmapWidth.toInt(), R.drawable.avatar_rengwuxian)

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.save()
        canvas?.translate(Utils.dp2px(200f), Utils.dp2px(200f))
        canvas?.rotate(-20f)
        canvas?.clipRect(-bitmapWidth, -bitmapWidth, bitmapWidth, 0f)
        canvas?.rotate(20f)
        canvas?.translate(Utils.dp2px(-200f), Utils.dp2px(-200f))
        canvas?.drawBitmap(bitmap, Utils.dp2px(100f), Utils.dp2px(100f), paint)
        canvas?.restore()



        canvas?.save()
        canvas?.translate(Utils.dp2px(200f), Utils.dp2px(200f))
        canvas?.rotate(-20f)
        camera.applyToCanvas(canvas)
        canvas?.clipRect(-bitmapWidth, 0f, bitmapWidth, bitmapWidth)
        canvas?.rotate(20f)
        canvas?.translate(Utils.dp2px(-200f), Utils.dp2px(-200f))
        canvas?.drawBitmap(bitmap, Utils.dp2px(100f), Utils.dp2px(100f), paint)
        canvas?.restore()



    }



    private fun getAvatar(width: Int, resourceId: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, resourceId, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, resourceId, options)
    }



}
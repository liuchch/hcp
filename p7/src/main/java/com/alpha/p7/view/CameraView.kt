package com.alpha.p7.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.alpha.p7.R
import com.alpha.p7.Utils

class CameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var midX = 0
    var midY = 0

    var camera = Camera()

    val bitmap: Bitmap

    init {
        paint.setColor(resources.getColor(R.color.red))


        bitmap = getAvatar(Utils.dp2px(100f).toInt(), R.drawable.avatar_rengwuxian)

        camera.rotateX(30f)
        camera.setLocation(0f, 0f, -8f)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.translate(Utils.dp2px(150f), Utils.dp2px(150f))
        camera.applyToCanvas(canvas)
        canvas?.translate(Utils.dp2px(-150f), Utils.dp2px(-150f))
        canvas?.drawBitmap(bitmap, Utils.dp2px(100f), Utils.dp2px(100f), paint)


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
package com.alpha.p7.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.alpha.p7.R
import com.alpha.p7.Utils

class ImageTextView(context: Context?, attrs: AttributeSet?)  : View(context, attrs){

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var midX = 0f
    var midY = 0f
    val radius = Utils.dp2px(100f)

    val rect = Rect()

    val textString = "庆历四年的春天，滕子京被降职到巴陵郡做太守。隔了一年，政治清明通达，人民安居和顺，各种荒废的事业都兴办起来了。于是重新修建岳阳楼，扩大它原有的规模，把唐代名家和当代人的诗赋刻在它上面。嘱托我写一篇文章来记述这件事情。我观看那巴陵郡的美好景色，全在洞庭湖上。衔接远山，吞没长江，流水浩浩荡荡，无边无际，一天里阴晴多变，气象千变万化。这就是岳阳楼的雄伟景象。前人的记述（已经）很详尽了。那么向北面通到巫峡，向南面直到潇水和湘水，降职的官吏和来往的诗人，大多在这里聚会，（他们)观赏自然景物而触发的感情大概会有所不同吧？像那阴雨连绵，接连几个月不放晴，寒风怒吼，浑浊的浪冲向天空；太阳和星星隐藏起光辉，山岳隐没了形体；商人和旅客（一译：行商和客商）不能通行，船桅倒下，船桨折断；傍晚天色昏暗，虎在长啸，猿在悲啼，（这时）登上这座楼，就会有一种离开国都、怀念家乡，担心人家说坏话、惧怕人家批评指责，满眼都是萧条的景象，感慨到了极点而悲伤的心情。"

    init {
        paint.textSize = Utils.dp2px(30f).toFloat()
        paint.textAlign = Paint.Align.LEFT
    }

    var cutWidth = FloatArray(1)

    var drawDirection = DrawImageDirection.All

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        midX = width/2f
        midY = height/2f

        val imageWidth = Utils.dp2px(200f)
        val imageHeight = Utils.dp2px(200f)
        val imageLeft = width - imageWidth - Utils.dp2px(80f)
        val imageTop = Utils.dp2px(50f)
        val imageRight = imageLeft + imageWidth
        val imageBottom = imageTop + imageHeight


        canvas?.drawBitmap(getAvatar(Utils.dp2px(200f).toInt()), imageLeft.toFloat(), Utils.dp2px(50f).toFloat(), paint)



        var startTextIndex = 0
        var endTextIndex = 0
        var startX = 0f
        var startY = 0f
        var text = textString
        paint.getTextBounds(text, 0, text.length, rect)
        val textHeight = rect.bottom - rect.top

        while (true) {
            var index = 0

            if (startY + textHeight < imageTop || startY > imageBottom) {
                index = paint.breakText(text, true, width.toFloat(), cutWidth)
                startX = 0f
                drawDirection = DrawImageDirection.All
            } else {
                if (drawDirection == DrawImageDirection.All || drawDirection == DrawImageDirection.Ritht) {
                    drawDirection = DrawImageDirection.Left
                    index = paint.breakText(text, true, imageLeft.toFloat(), cutWidth)
                    startX = 0f
                } else {
                    drawDirection = DrawImageDirection.Ritht
                    index = paint.breakText(text, true, width - imageRight.toFloat(), cutWidth)
                    startX = imageRight.toFloat()
                }
            }



            endTextIndex = index
            if (startTextIndex >= text.length) {
                break
            }
            if (endTextIndex > text.length) {
                endTextIndex = text.length
            }
//            paint.getTextBounds(text, startTextIndex, endTextIndex, rect)
            startY += rect.bottom-rect.top
            canvas?.drawText(text, startTextIndex, endTextIndex, startX, startY, paint)
            text = text.substring(endTextIndex)
            if (drawDirection == DrawImageDirection.Left) {
                startY -= rect.bottom - rect.top
            }
        }


    }


    private fun getAvatar(width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, options)
    }
}


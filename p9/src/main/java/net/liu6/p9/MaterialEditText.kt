package net.liu6.p9

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.alpha.utils.dp2px



class MaterialEditText(context: Context?, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {

    val titleTextSize = dp2px(16)
    val titleMargin = dp2px(16)

    var backgroundPadding = Rect()


    init {
        background.getPadding(backgroundPadding)
        setPadding(paddingLeft, (backgroundPadding.top + titleTextSize + titleMargin).toInt(), paddingRight, paddingBottom)
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


    }





}
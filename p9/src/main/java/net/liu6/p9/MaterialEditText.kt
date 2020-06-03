package net.liu6.p9

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.alpha.utils.dp2px






class MaterialEditText(context: Context?, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {

    val titleTextSize = dp2px(16)
    val titleMargin = dp2px(16)

    var backgroundPadding = Rect()

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var useFloatingLabel: Boolean = true

    private var animator =  ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0f, 1f)

    var floatingLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }


    var floatingLabelShown = false

    fun setNeedUseFloatingLabel(useFloatingLabel: Boolean) {
        if (this.useFloatingLabel != useFloatingLabel) {
            this.useFloatingLabel = useFloatingLabel
            onUseFloatingLabelChanged()
        }
    }

    private fun onUseFloatingLabelChanged() {
        background.getPadding(backgroundPadding)
        if (useFloatingLabel) {
            setPadding(
                paddingLeft,
                (backgroundPadding.top + titleTextSize + titleMargin).toInt(),
                paddingRight,
                paddingBottom
            )
        } else {
            setPadding(paddingLeft, backgroundPadding.top, paddingRight, paddingBottom)
        }
    }

    init {

        val typeArray = context!!.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
        useFloatingLabel = typeArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel, true)
        typeArray.recycle()

        paint.textSize = titleTextSize
        paint.color = resources.getColor(R.color.colorAccent)

        onUseFloatingLabelChanged()


        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (useFloatingLabel) {
                    if (floatingLabelShown && s.isNullOrEmpty()) {
                        floatingLabelShown = !floatingLabelShown
                        animator.reverse()
                    } else if (!floatingLabelShown && !s.isNullOrEmpty()) {
                        floatingLabelShown = !floatingLabelShown

                        animator.start()
                    }
                }
            }
        })
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.alpha = (0xff * floatingLabelFraction).toInt()
        canvas?.drawText(hint.toString(), paddingStart.toFloat(), titleTextSize + (1-floatingLabelFraction) * titleMargin, paint)

    }





}
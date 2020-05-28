package com.alpha.p8

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomFlipAnimator = ObjectAnimator.ofFloat(flipView, "bottomFlipAngle", 0f, 45f)
        bottomFlipAnimator.duration = 1500

        val rotateAnimator = ObjectAnimator.ofFloat(flipView, "rotateAngle", 0f, 270f)
        rotateAnimator.duration = 1500

        val topFlipAnimator = ObjectAnimator.ofFloat(flipView, "topFlipAngle", 0f, -45f)
        bottomFlipAnimator.duration = 1500

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(bottomFlipAnimator, rotateAnimator, topFlipAnimator)
        animatorSet.startDelay = 1000
        animatorSet.start()
    }
}

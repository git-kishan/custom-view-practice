package com.example.testapp2

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

class CustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private lateinit var paint : Paint
    private var circleRadius = 100F
    private var valueAnimator : ValueAnimator? = null

    init {
        val typedArray = context?.obtainStyledAttributes(attrs,R.styleable.CustomView)
        val loadingColor = typedArray?.getColor(R.styleable.CustomView_progress_color,Color.BLACK)

        paint = Paint().apply {
            color = loadingColor?:Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 20F
        }
        typedArray?.recycle()
    }

     fun showLoading(){
        valueAnimator = ValueAnimator.ofFloat(10f,circleRadius)
        valueAnimator.apply {
            this?.duration = 1000
            this?.interpolator = AccelerateDecelerateInterpolator()

            valueAnimator?.addUpdateListener {
                circleRadius = it.animatedValue as Float
                it.repeatCount = ValueAnimator.INFINITE
                it.repeatMode = ValueAnimator.REVERSE
                invalidate()
            }
        }
        valueAnimator?.start()



    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(width/2F,height/2F, circleRadius, paint)
    }


}
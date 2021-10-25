package com.example.testapp2

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.min

class CircularProgressView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    private var mSize = 0
    private var paint : Paint? = null
    private var mRectF : RectF? = null
    private var valueAnimator : ValueAnimator? = null
    private var indeterminateSweep  = 0f
    private var mStartAngle = 0f

    init {
        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val xPad = paddingLeft + paddingRight
        val yPad = paddingTop + paddingBottom
        val width = measuredWidth - xPad
        val height = measuredHeight - yPad
        mSize = min(height,width)
        setMeasuredDimension(mSize + xPad , mSize + yPad)
    }

    private fun init(){
        paint = Paint().apply {
            isAntiAlias = true
            color = Color.RED
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 8f
            style = Paint.Style.STROKE
            mRectF = RectF(200f,200f,800f,800f)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawArc(mRectF!!,mStartAngle,indeterminateSweep,false,paint!!)
    }

    fun start(){
        valueAnimator = ValueAnimator.ofFloat(0f,360f).apply {
            duration = 1000
            interpolator = LinearInterpolator()
            addUpdateListener {
                mStartAngle = it.animatedValue as Float
                indeterminateSweep+=2
                if(indeterminateSweep>360)
                    indeterminateSweep = 15f
                invalidate()
            }
            addListener(object : Animator.AnimatorListener{
                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                    super.onAnimationEnd(animation, isReverse)
                    start()
                    Log.v("kishanlogs","onAnimationEnds")
                }

                override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                    super.onAnimationStart(animation, isReverse)
                }
            })
        }
        valueAnimator?.start()
    }



    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if(valueAnimator != null){
            valueAnimator?.cancel()
            valueAnimator?.removeAllListeners()
            valueAnimator = null
        }
    }

}
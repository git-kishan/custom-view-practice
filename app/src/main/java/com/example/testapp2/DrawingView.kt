package com.example.testapp2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
data class Point(val x : Float, val y : Float)
class DrawingView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paint : Paint? = null
    private var path : Path? = null

    init {
        paint= Paint().apply {
            isAntiAlias = true
            strokeWidth = 6f
            color = Color.WHITE
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
            style= Paint.Style.STROKE
        }
        path = Path()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path!!,paint!!)
    }



    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x as Float
        val y = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                path?.moveTo(x,y)
            }

            MotionEvent.ACTION_MOVE -> {
                path?.lineTo(x,y)
            }
        }
        invalidate()
        return true

    }


}
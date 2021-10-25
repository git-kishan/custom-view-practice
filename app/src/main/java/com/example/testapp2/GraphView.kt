package com.example.testapp2

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.*

class GraphView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val coordinates = mutableListOf<Point>()
    val coordinatesVertices = mutableListOf<Point>()

    private var axisPath : Path? = null
    private var graphAxisPath : Path? = null
    private var graphLinePath : Path? = null

    private var originX = 0f
    private var originY = 0f
    private var xUnit = 0f
    private var yUnit = 0f

    private var axisPaint : Paint? = null
    private var graphAxisPaint : Paint? = null
    private var graphLinePaint : Paint? = null

    private var drawingPath : Path? = null
    private var pathMeasure : PathMeasure? = null
    private var pathLength : Float = 0f
    private var position  = FloatArray(2)

    private var graphAnimator : ValueAnimator? = null


    init {

        axisPath = Path()
        graphAxisPath = Path()
        graphLinePath = Path()
        drawingPath = Path()

        axisPaint = Paint().apply {
            isAntiAlias = true
            strokeWidth = 5f
            style = Paint.Style.STROKE
            color = Color.parseColor("#000000")
            strokeJoin = Paint.Join.ROUND
            setAlpha(1f)
            strokeCap = Paint.Cap.ROUND
        }

        graphAxisPaint = Paint().apply {
            isAntiAlias = true
            strokeWidth = 0.2f
            setAlpha(0.01f)
            color = Color.parseColor("#000000")
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            style = Paint.Style.STROKE
        }

        graphLinePaint = Paint().apply {
            isAntiAlias = true
            setAlpha(1f)
            strokeWidth = 5f
            color = Color.parseColor("#3495eb")
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            style = Paint.Style.STROKE
        }

    }


     fun start(){
         postInvalidate()
        coordinates.clear()
         coordinatesVertices.clear()
        for( i in 1..10){
            val x = (0..((measuredWidth-2*xUnit).toInt())).random()
            val y = (0..(measuredHeight-2*yUnit).toInt()).random()
            coordinates.add(Point(x.toFloat(),y.toFloat()))
        }
        coordinates.sortBy { it.x }

         graphLinePath?.reset()
         graphLinePath?.moveTo(originX,originY)

         for(i in 0..coordinates.size-1){
             val actualX = coordinates[i].x + xUnit
             val actualY = -coordinates[i].y + measuredHeight-yUnit
             graphLinePath?.lineTo(actualX ,actualY)
             coordinatesVertices.add(Point(actualX,actualY))
         }


         drawingPath?.reset()
         drawingPath?.moveTo(originX,originY)

         pathMeasure = PathMeasure(graphLinePath,false)
         pathLength = pathMeasure?.length ?: 0f
         position[0] = originX
         position[1] = originY

         graphAnimator = ValueAnimator.ofFloat(0f,1f).apply {
             duration = 2000
             val mpath = Path()
             mpath.moveTo(0f,0f)
             mpath.lineTo(1f,1f)
             interpolator = PathInterpolator(mpath)
             addUpdateListener {
                 val distance = pathLength * (it.animatedValue as Float)
                 pathMeasure?.getPosTan(distance,position,null)
                 drawingPath?.lineTo(position[0],position[1])
                 postInvalidate()
             }
         }
         graphAnimator?.start()

        invalidate()
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        xUnit = (measuredWidth/12).toFloat()
        yUnit = (measuredHeight/12).toFloat()
        originX = xUnit
        originY = measuredHeight - yUnit

        axisPath?.apply {
            reset()
            moveTo(originX,originY)
            lineTo(originX,yUnit)
            moveTo(originX,originY)
            lineTo(measuredWidth-xUnit,originY)
        }

        var yArgs = originY-yUnit
        var xArgs = originX + xUnit

        for(i in 1..10){
            graphAxisPath?.moveTo(originX,yArgs)
            graphAxisPath?.lineTo(measuredWidth-xUnit,yArgs)
            graphAxisPath?.moveTo(xArgs,yUnit)
            graphAxisPath?.lineTo(xArgs,originY)
            yArgs-=yUnit
            xArgs+=xUnit
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawAxis(it)
        }
    }

    private fun drawAxis(canvas : Canvas){
        canvas.drawPath(axisPath!!,axisPaint!!)
        canvas.drawPath(graphAxisPath!!,graphAxisPaint!!)
        canvas.drawPath(drawingPath!!,graphLinePaint!!)

    }

}
package com.example.opengl2.util

import android.util.SparseLongArray
import com.example.opengl2.shape.Treasure
import java.util.ArrayList

/**
 * Created by hezhanghe on 2020-02-16.
 * github: https://github.com/HadesHe
 */
object Cons {

    val BALL_LIGHT: String="BallLight"
    val BALL_AMBIENT: String = "BallAmbient"
    val TEXTURE:String="Texture"
    val VERTEX_BALL: FloatArray = ballVert(0.8f, 180)
    val UNIT_SIZE = 1
    val VERTEX_RING: FloatArray = ringVertex(360, 0.5f, 1f)
    val COLOR_RING = ringColor()
    var ringVertexCount = 0
    val DIMENSION_2 = 2
    val DIMENSION_3 = 3
    val DIMENSION_4 = 4

    val VERTEX_COO = floatArrayOf(
        0f, 0f, 0f,
        0f, 0f, 1f,
        0f, 0f, 0f,
        1f, 0f, 0f,
        0f, 0f, 0f,
        0f, 1f, 0f
    )
    val COLOR_COO = floatArrayOf(
        0f, 0f, 1f, 1f,
        0f, 0f, 1f, 1f,
        1f, 1f, 0f, 1f,
        1f, 1f, 0f, 1f,
        0f, 1f, 0f, 1f,
        0f, 1f, 0f, 1f
    )


    val TREASURE_COO = gen(0.2f)

    val TREASURE_COLOR = FloatArray(TREASURE_COO.size / 3 * 4, { 1.0f })


    fun ringVertex(splitCount: Int, r: Float, R: Float): FloatArray {
        ringVertexCount = splitCount * 2 + 2
        val vertices = FloatArray(ringVertexCount * 3)
        val thta = 360f / splitCount
        for (i in 0 until vertices.size step 3) {
            val n = i / 3
            when (n % 2) {
                0 -> {
                    vertices[i] = r * Math.cos(n * thta.toDouble()).toFloat()//x
                    vertices[i + 1] = r * Math.sin(n * thta.toDouble()).toFloat()//x
                    vertices[i + 2] = 0f//z

                }
                else -> {
                    vertices[i] = R * Math.cos(n * thta.toDouble()).toFloat()//x
                    vertices[i + 1] = R * Math.sin(n * thta.toDouble()).toFloat()//x
                    vertices[i + 2] = 0f//z
                }
            }
        }

        return vertices

    }

    fun ringColor(): FloatArray {
        var colors = FloatArray(ringVertexCount * 4)
        for (i in 0 until colors.size step 8) {
            colors[i] = 1f
            colors[i + 1] = 1f
            colors[i + 2] = 1f
            colors[i + 3] = 1f
            colors[i + 4] = 0.972549f
            colors[i + 5] = 0.5019608f
            colors[i + 6] = 0.09411756f
            colors[i + 7] = 1f
        }
        return colors
    }


    fun gen(radiu: Float): FloatArray {
        val firstEnd = 90
        var circleArray = FloatArray((firstEnd + 270) * 3)

        circleArray[0] = 0f
        circleArray[1] = 0f
        circleArray[2] = 0f


        for (i in 1 until firstEnd) {
            var x = 1 - Math.sin(Math.PI * i / 180f).toFloat()
            var y = 1 - Math.cos(Math.PI * i / 180f).toFloat()
            circleArray[(i) * 3] = x * radiu
            circleArray[(i) * 3 + 1] = y * radiu
            circleArray[(i) * 3 + 2] = 0f

        }

        for (i in 90 until 180) {
            var x = -1 + Math.sin(Math.PI * i / 180f).toFloat()
            var y = 1 + Math.cos(Math.PI * i / 180f).toFloat()
            circleArray[(i) * 3] = x * radiu
            circleArray[(i) * 3 + 1] = y * radiu
            circleArray[(i) * 3 + 2] = 0f
        }

        for (i in 180 until 270) {
            var x = -1 - Math.sin(Math.PI * i / 180f).toFloat()
            var y = -1 - Math.cos(Math.PI * i / 180f).toFloat()
            circleArray[(i) * 3] = x * radiu
            circleArray[(i) * 3 + 1] = y * radiu
            circleArray[(i) * 3 + 2] = 0f
        }

        for (i in 270 until 360) {
            var x = 1 + Math.sin(Math.PI * i / 180f).toFloat()
            var y = -1 + Math.cos(Math.PI * i / 180f).toFloat()
            circleArray[(i) * 3] = x * radiu
            circleArray[(i) * 3 + 1] = y * radiu
            circleArray[(i) * 3 + 2] = 0f
        }

        return circleArray

    }

    fun ballVert(r: Float, splitCount: Int): FloatArray {
        val vertixs = ArrayList<Float>()// 存放顶点坐标的ArrayList
        val dθ = 360f / splitCount// 将球进行单位切分的角度
        //垂直方向angleSpan度一份
        var α = -90f
        while (α < 90) {
            // 水平方向angleSpan度一份
            var β = 0f
            while (β <= 360) {
                // 纵向横向各到一个角度后计算对应的此点在球面上的坐标
                val x0 = r * cos(α) * cos(β)
                val y0 = r * cos(α) * sin(β)
                val z0 = r * sin(α)

                val x1 = r * cos(α) * cos(β + dθ)
                val y1 = r * cos(α) * sin(β + dθ)
                val z1 = r * sin(α)

                val x2 = r * cos(α + dθ) * cos(β + dθ)
                val y2 = r * cos(α + dθ) * sin(β + dθ)
                val z2 = r * sin(α + dθ)

                val x3 = r * cos(α + dθ) * cos(β)
                val y3 = r * cos(α + dθ) * sin(β)
                val z3 = r * sin(α + dθ)

                // 将计算出来的XYZ坐标加入存放顶点坐标的ArrayList
                vertixs.add(x1)
                vertixs.add(y1)
                vertixs.add(z1)//p1
                vertixs.add(x3)
                vertixs.add(y3)
                vertixs.add(z3)//p3
                vertixs.add(x0)
                vertixs.add(y0)
                vertixs.add(z0)//p0
                vertixs.add(x1)
                vertixs.add(y1)
                vertixs.add(z1)//p1
                vertixs.add(x2)
                vertixs.add(y2)
                vertixs.add(z2)//p2
                vertixs.add(x3)
                vertixs.add(y3)
                vertixs.add(z3)//p3
                β = β + dθ
            }
            α = α + dθ
        }

        val verticeCount = vertixs.size / 3// 顶点的数量为坐标值数量的1/3，因为一个顶点有3个坐标
        // 将vertices中的坐标值转存到一个float数组中
        val vertices = FloatArray(verticeCount * 3)
        for (i in 0 until vertixs.size) {
            vertices[i] = vertixs.get(i)
        }
        return vertices

    }

    fun sin(a: Float): Float {
        return Math.sin(Math.toRadians(a.toDouble())).toFloat()
    }

    fun cos(a: Float): Float {
        return Math.cos(Math.toRadians(a.toDouble())).toFloat()
    }

}
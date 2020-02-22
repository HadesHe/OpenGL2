package com.example.opengl2.util

import android.util.SparseLongArray
import com.example.opengl2.shape.Treasure

/**
 * Created by hezhanghe on 2020-02-16.
 * github: https://github.com/HadesHe
 */
object Cons {

    val VERTEX_RING: FloatArray = ringVertex(360, 0.5f, 1f)
    val COLOR_RING= ringColor()
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

}
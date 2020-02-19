package com.example.opengl2.util

import com.example.opengl2.shape.Treasure

/**
 * Created by hezhanghe on 2020-02-16.
 * github: https://github.com/HadesHe
 */
object Cons {

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
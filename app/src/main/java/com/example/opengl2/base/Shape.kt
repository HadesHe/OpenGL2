package com.example.opengl2.base

import com.example.opengl2.util.Cons

/**
 * Created by hezhanghe on 2020-02-18.
 * github: https://github.com/HadesHe
 */
data class Shape(var mVertex: FloatArray, var mColor: FloatArray, var mDrawType: Int) : Cloneable {
    fun getCount(): Int = mVertex.size / Cons.DIMENSION_3

    public override fun clone(): Shape {
        var clone: Shape? = null
        try {
             clone = super.clone() as Shape
            var vertex = FloatArray(mVertex.size)
            var color = FloatArray(mColor.size)
            System.arraycopy(mVertex, 0, vertex, 0, mVertex.size)
            System.arraycopy(mColor, 0, color, 0, mColor.size)
            clone.mVertex = vertex
            clone.mColor = color
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        return clone!!
    }

    fun moveAndCreate(x: Float, y: Float, z: Float): Shape {
        var clone = clone()
        clone.move(x, y, z)
        return clone
    }

    fun move(x: Float, y: Float, z: Float) {
        mVertex.forEachIndexed { index, fl ->
            when (index % 3) {
                0 -> {
                    mVertex[index] = fl + x

                }
                1 -> {
                    mVertex[index] = fl + y

                }
                2 -> {
                    mVertex[index] = fl + z

                }
            }
        }

    }

}
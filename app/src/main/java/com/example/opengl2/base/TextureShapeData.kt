package com.example.opengl2.base

import com.example.opengl2.util.Cons

/**
 * Created by hezhanghe on 2020-02-24.
 * github: https://github.com/HadesHe
 */
class TextureShapeData(
    var mVertex: FloatArray,
    var mTextureArray: FloatArray,
    var mRedId: Int
) : Cloneable {

    fun getCount() = mVertex.size / Cons.DIMENSION_3


    override fun clone(): TextureShapeData {
        var clone: TextureShapeData? = null
        try {
            clone = super.clone() as TextureShapeData
            var vertex = FloatArray(mVertex.size)
            System.arraycopy(mVertex, 0, vertex, 0, mVertex.size)
            var texture = FloatArray(mTextureArray.size)
            System.arraycopy(mTextureArray, 0, texture, 0, mTextureArray.size)
            clone.mTextureArray = texture
            clone.mVertex = vertex
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        return clone!!
    }
}
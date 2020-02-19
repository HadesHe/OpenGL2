package com.example.opengl2.shape

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLU
import com.example.opengl2.base.RenderAble
import com.example.opengl2.base.Shape
import com.example.opengl2.util.Cons
import com.example.opengl2.util.GLUtil
import com.example.opengl2.util.toFloatBuffer
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL

/**
 * Created by hezhanghe on 2020-02-18.
 * github: https://github.com/HadesHe
 */
class SimpleShape(context: Context, val shape: Shape) : RenderAble(context) {

    private var muMVPMatrixHandle: Int = 0
    private var mColorHandle: Int = 0
    private var mPositionHandle = 0
    private var mProgram: Int = 0
    private var mVertexBuffer: FloatBuffer?
    private var mColorBuffer: FloatBuffer?
    private val vertexStride = Cons.DIMENSION_3 * 4
    private val vertexColorStride = Cons.DIMENSION_4 * 4

    init {
        mColorBuffer = shape.mColor.toFloatBuffer()
        mVertexBuffer = shape.mVertex.toFloatBuffer()
        initProgram()
    }

    private fun initProgram() {
        val vertexShader = GLUtil.loadVertexShaderAssets(context, "world.vert")
        val fragmentShader = GLUtil.loadFragShaderAssets(context, "world.frag")
        mProgram = GLES20.glCreateProgram()
        GLES20.glAttachShader(mProgram, vertexShader)
        GLES20.glAttachShader(mProgram, fragmentShader)
        GLES20.glLinkProgram(mProgram)
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor")
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
    }

    override fun draw(mvpMatrix: FloatArray) {
        GLES20.glUseProgram(mProgram)
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        GLES20.glEnableVertexAttribArray(mColorHandle)
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mvpMatrix, 0)
        GLES20.glVertexAttribPointer(
            mPositionHandle,
            Cons.DIMENSION_3,
            GLES20.GL_FLOAT,
            false,
            vertexStride,//跨度
            mVertexBuffer
        )
        GLES20.glVertexAttribPointer(
            mColorHandle,
            Cons.DIMENSION_4,
            GLES20.GL_FLOAT,
            false,
            vertexColorStride,
            mColorBuffer
        )
        GLES20.glLineWidth(9.9f)
        GLES20.glDrawArrays(shape.mDrawType, 0, shape.getCount())
    }

}
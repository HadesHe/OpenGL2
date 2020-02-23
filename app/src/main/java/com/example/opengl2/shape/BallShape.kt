package com.example.opengl2.shape

import android.content.Context
import android.opengl.GLES20
import com.example.opengl2.base.RenderAble
import com.example.opengl2.base.Shape
import com.example.opengl2.util.Cons
import com.example.opengl2.util.GLUtil
import com.example.opengl2.util.toFloatBuffer
import java.nio.FloatBuffer

/**
 * Created by hezhanghe on 2020-02-22.
 * github: https://github.com/HadesHe
 */
class BallShape(context: Context, val shape: Shape, val mR: Float) : RenderAble(context) {

    private var muRHandle: Int = 0
    private var muMVPMatrixHandle: Int = 0
    private var maPositionHandle: Int = 0
    private var mProgram: Int = 0
    private var mVertexBuffer: FloatBuffer?

    init {
        mVertexBuffer = shape.mVertex.toFloatBuffer()
        initProgram()
    }

    private fun initProgram() {
        val vertexShader = GLUtil.loadVertexShaderAssets(context, "ball.vert")
        val fragmentShader = GLUtil.loadFragShaderAssets(context, "ball.frag")
        mProgram = GLES20.glCreateProgram()
        GLES20.glAttachShader(mProgram, vertexShader)
        GLES20.glAttachShader(mProgram, fragmentShader)
        GLES20.glLinkProgram(mProgram)

        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition")
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
        muRHandle = GLES20.glGetUniformLocation(mProgram, "uR")
    }

    override fun draw(mvpMatrix: FloatArray) {
        GLES20.glUseProgram(mProgram)
        GLES20.glEnableVertexAttribArray(maPositionHandle)
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mvpMatrix, 0)
        GLES20.glVertexAttribPointer(
            maPositionHandle, 3,
            GLES20.GL_FLOAT, false, 3 * 4,
            mVertexBuffer
        )
        GLES20.glUniform1f(muRHandle, mR * Cons.UNIT_SIZE)
        GLES20.glDrawArrays(shape.mDrawType, 0, shape.getCount())
    }

}
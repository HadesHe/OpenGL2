package com.example.opengl2.shape

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import com.example.opengl2.base.RenderAble
import com.example.opengl2.base.Shape
import com.example.opengl2.util.Cons
import com.example.opengl2.util.GLUtil
import com.example.opengl2.util.toFloatBuffer
import java.nio.FloatBuffer

/**
 * Created by hezhanghe on 2020-02-25.
 * github: https://github.com/HadesHe
 */
class ObjShape(context: Context, val shape: Shape) : RenderAble(context) {
    private var muMVPMatrixHandle = 0
    private var mPositionHandle = 0
    private var mProgram: Int = 0
    private var mVertexBuffer: FloatBuffer?
    private var mMVPMatrix = FloatArray(16)

    init {
        mVertexBuffer = shape.mVertex.toFloatBuffer()

        initProgram()
    }

    private fun initProgram() {
        val vertexShader = GLUtil.loadVertexShaderAssets(context, "obj.vert")
        val fragShader = GLUtil.loadFragShaderAssets(context, "obj.frag")
        mProgram = GLES20.glCreateProgram()
        GLES20.glAttachShader(mProgram, vertexShader)
        GLES20.glAttachShader(mProgram, fragShader)
        GLES20.glLinkProgram(mProgram)

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition")
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
    }


    override fun draw(mvpMatrix: FloatArray) {
        GLES20.glUseProgram(mProgram)
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        Matrix.scaleM(mMVPMatrix, 0, mvpMatrix, 0, 0.02f, 0.02f, 0.02f)
        Matrix.translateM(mMVPMatrix, 0, -230f, -50f, 30f)
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0)
        GLES20.glVertexAttribPointer(
            mPositionHandle,
            Cons.DIMENSION_3,
            GLES20.GL_FLOAT,
            false,
            Cons.DIMENSION_3 * 4,
            mVertexBuffer
        )

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, shape.getCount())
        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }

}
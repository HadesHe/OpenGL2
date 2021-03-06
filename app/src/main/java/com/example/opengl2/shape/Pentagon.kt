package com.example.opengl2.shape

import android.content.Context
import android.opengl.GLES20
import com.example.opengl2.util.GLUtil
import com.example.opengl2.util.toFloatBuffer
import com.example.opengl2.util.toShortBuffer
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * Created by hezhanghe on 2020-02-13.
 * github: https://github.com/HadesHe
 */
class Pentagon(context: Context) {

    private var idxBuffer: ShortBuffer?
    private var muMVPMatrixHandle: Int = 0
    private var mColorBuffer: FloatBuffer?
    private var mColorHandle: Int = 0
    private var mPositionHandle: Int = 0
    private var mProgram: Int = 0
    private var vertexBuffer: FloatBuffer?

    init {

        vertexBuffer= sCoor.toFloatBuffer()
        mColorBuffer= colors.toFloatBuffer()
        idxBuffer= idx.toShortBuffer()

        val vertexShader = GLUtil.loadShaderAssets(context, GLES20.GL_VERTEX_SHADER, "tri.vert")
        val fragmentShader = GLUtil.loadShaderAssets(context, GLES20.GL_FRAGMENT_SHADER, "tri.frag")
        mProgram = GLES20.glCreateProgram()
        GLES20.glAttachShader(mProgram, vertexShader)
        GLES20.glAttachShader(mProgram, fragmentShader)
        GLES20.glLinkProgram(mProgram)
    }

    fun draw(mvpMatrix: FloatArray) {
        GLES20.glUseProgram(mProgram)
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        GLES20.glVertexAttribPointer(
            mPositionHandle, COORD_PER_VERTEX,
            GLES20.GL_FLOAT, false, vertexStride, vertexBuffer
        )
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, idx.size,GLES20.GL_UNSIGNED_SHORT,idxBuffer)
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mvpMatrix, 0)

        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor")
        GLES20.glEnableVertexAttribArray(mColorHandle)
        GLES20.glVertexAttribPointer(
            mColorHandle, COLOR_PER_VERTEX,
            GLES20.GL_FLOAT, false, vertexColorStride, mColorBuffer
        )


        GLES20.glDisableVertexAttribArray(mPositionHandle)
//        GLES20.glDisableVertexAttribArray(mColorHandle)
    }

    companion object {
        val sCoor = floatArrayOf(
            0.5f,0.5f,0f,
            0f,0.8f,0f,
            -0.5f,0.5f,0f,
            -0.5f,-0.5f,0f,
            0.5f,-0.5f,0f
        )

        val colors = floatArrayOf(
            1f, 1f, 0f, 1f,
            0.05882353f, 0.09411765f, 0.9372549f, 1f,
            0.19607843f, 1.0f, 0.02745098f, 1.0f,
            1f,0f,1f,1f,
            0f,1f,0f,1f
        )

        val idx= shortArrayOf(
            0,1,2,2,3,0,4,0,3
        )
        val COORD_PER_VERTEX = 3

        val COLOR_PER_VERTEX = 4

        val vertexCount = sCoor.size / COORD_PER_VERTEX
        val vertexStride = COORD_PER_VERTEX * 4
        val vertexColorStride = COLOR_PER_VERTEX * 4


    }
}
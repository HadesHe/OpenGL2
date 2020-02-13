package com.example.opengl2.shape

import android.content.Context
import android.opengl.GLES20
import com.example.opengl2.GLUtil
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Created by hezhanghe on 2020-02-12.
 * github: https://github.com/HadesHe
 */
class Triangle(val context: Context) {

    private var mColorBuffer: FloatBuffer
    private var mColorHandle: Int = 0
    private var mPositionHandle: Int = 0
    private var mProgram: Int = 0
    private var vertexBuffer: FloatBuffer

    init {
        val bb = ByteBuffer.allocateDirect(sCoor.size * 4)
        bb.order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(sCoor)
        vertexBuffer.position(0)

        mColorBuffer = ByteBuffer.allocateDirect(colors.size * 4).order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        mColorBuffer.put(colors)
        mColorBuffer.position(0)
        val vertexShader = GLUtil.loadShaderAssets(context, GLES20.GL_VERTEX_SHADER, "tri.vert")
        val fragmentShader = GLUtil.loadShaderAssets(context, GLES20.GL_FRAGMENT_SHADER, "tri.frag")
        mProgram = GLES20.glCreateProgram()
        GLES20.glAttachShader(mProgram, vertexShader)
        GLES20.glAttachShader(mProgram, fragmentShader)
        GLES20.glLinkProgram(mProgram)
    }

    fun draw() {
        GLES20.glUseProgram(mProgram)
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        GLES20.glVertexAttribPointer(
            mPositionHandle, COORD_PER_VERTEX,
            GLES20.GL_FLOAT, false, vertexStride, vertexBuffer
        )

        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor")
        GLES20.glEnableVertexAttribArray(mColorHandle)
        GLES20.glVertexAttribPointer(
            mColorHandle, COLOR_PER_VERTEX,
            GLES20.GL_FLOAT, false, vertexColorStride, mColorBuffer
        )

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
        GLES20.glDisableVertexAttribArray(mPositionHandle)
        GLES20.glDisableVertexAttribArray(mColorHandle)
    }

    companion object {
        val sCoor = floatArrayOf(
            0f, 0f, 0f,
            -1f, -1f, 0f,
            1f, -1f, 0f
        )

        val colors = floatArrayOf(
            1f, 1f, 0f, 1f,
            0.05882353f, 0.09411765f, 0.9372549f, 1f,
            0.19607843f, 1.0f, 0.02745098f, 1.0f
        )
        val COORD_PER_VERTEX = 3

        val COLOR_PER_VERTEX = 4

        val vertexCount = sCoor.size / COORD_PER_VERTEX
        val vertexStride = COORD_PER_VERTEX * 4
        val vertexColorStride = COLOR_PER_VERTEX * 4


    }

}
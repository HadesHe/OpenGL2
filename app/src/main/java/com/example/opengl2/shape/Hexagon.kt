package com.example.opengl2.shape

import android.content.Context
import android.opengl.GLES20
import com.example.opengl2.util.GLUtil
import com.example.opengl2.util.getFloatBuffer
import com.example.opengl2.util.getShortBuffer
import java.nio.FloatBuffer
import java.nio.ShortBuffer

class Hexagon(context: Context) {
    private var idxBuffer: ShortBuffer?
    private var muMVPMatrixHandle: Int = 0
    private var mColorBuffer: FloatBuffer?
    private var mColorHandle: Int = 0
    private var mPositionHandle: Int = 0
    private var mProgram: Int = 0
    private var vertexBuffer: FloatBuffer?

    init {

        vertexBuffer = sCoor.getFloatBuffer()
        mColorBuffer = colors.getFloatBuffer()
        idxBuffer = idx.getShortBuffer()

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
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, idx.size, GLES20.GL_UNSIGNED_SHORT, idxBuffer)
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
            //A面
            -0.5f, 0.5f, 0.5f,//p0
            -0.5f, -0.5f, 0.5f,//p1
            -0.5f, -0.5f, -0.5f,//p2
            -0.5f, 0.5f, -0.5f,//p3
            //B面
            -0.5f, 0.5f, 0.5f,//p4
            -0.5f, -0.5f, 0.5f,//p5
            0.5f, -0.5f, 0.5f,//p6
            0.5f, 0.5f, 0.5f,//p7
            //C面
            0.5f, 0.5f, 0.5f,//p8
            0.5f, -0.5f, 0.5f,//p9
            0.5f, -0.5f, -0.5f,//p10
            0.5f, 0.5f, -0.5f,//p11
            //D面
            -0.5f, 0.5f, 0.5f,//p12
            0.5f, 0.5f, 0.5f,//p13
            0.5f, 0.5f, -0.5f,//p14
            -0.5f, 0.5f, -0.5f,//p15
            //E面
            -0.5f, -0.5f, 0.5f,//p16
            0.5f, -0.5f, 0.5f,//p17
            0.5f, -0.5f, -0.5f,//p18
            -0.5f, -0.5f, -0.5f,//p19
            //F面
            -0.5f, 0.5f, -0.5f,//p20
            -0.5f, -0.5f, -0.5f,//p21
            0.5f, -0.5f, -0.5f,//p22
            0.5f, 0.5f, -0.5f//p23

        )

        val colors = floatArrayOf(
            //A
            1f, 1f, 0.0f, 1.0f,//黄
            0.05882353f, 0.09411765f, 0.9372549f, 1.0f,//蓝
            0.19607843f, 1.0f, 0.02745098f, 1.0f,//绿
            1.0f, 0.0f, 1.0f, 1.0f,//紫色
            //B
            1f, 1f, 0.0f, 1.0f,//黄
            0.05882353f, 0.09411765f, 0.9372549f, 1.0f,//蓝
            0.19607843f, 1.0f, 0.02745098f, 1.0f,//绿
            1.0f, 0.0f, 1.0f, 1.0f,//紫色
            //C
            1f, 1f, 0.0f, 1.0f,//黄
            0.05882353f, 0.09411765f, 0.9372549f, 1.0f,//蓝
            0.19607843f, 1.0f, 0.02745098f, 1.0f,//绿
            1.0f, 0.0f, 1.0f, 1.0f,//紫色
            //D
            1f, 1f, 0.0f, 1.0f,//黄
            0.05882353f, 0.09411765f, 0.9372549f, 1.0f,//蓝
            0.19607843f, 1.0f, 0.02745098f, 1.0f,//绿
            1.0f, 0.0f, 1.0f, 1.0f,//紫色
            //E
            1f, 1f, 0.0f, 1.0f,//黄
            0.05882353f, 0.09411765f, 0.9372549f, 1.0f,//蓝
            0.19607843f, 1.0f, 0.02745098f, 1.0f,//绿
            1.0f, 0.0f, 1.0f, 1.0f,//紫色
            //F
            1f, 1f, 0.0f, 1.0f,//黄
            0.05882353f, 0.09411765f, 0.9372549f, 1.0f,//蓝
            0.19607843f, 1.0f, 0.02745098f, 1.0f,//绿
            1.0f, 0.0f, 1.0f, 1.0f//紫色

        )

        val idx = shortArrayOf(
            0, 1, 3,//A
            1, 2, 3,
            0 + 4, 1 + 4, 3 + 4,//B
            1 + 4, 2 + 4, 3 + 4,
            0 + 4 * 2, 1 + 4 * 2, 3 + 4 * 2,//C
            1 + 4 * 2, 2 + 4 * 2, 3 + 4 * 2,
            0 + 4 * 3, 1 + 4 * 3, 3 + 4 * 3,//D
            1 + 4 * 3, 2 + 4 * 3, 3 + 4 * 3,
            0 + 4 * 4, 1 + 4 * 4, 3 + 4 * 4,//E
            1 + 4 * 4, 2 + 4 * 4, 3 + 4 * 4,
            0 + 4 * 5, 1 + 4 * 5, 3 + 4 * 5,//F
            1 + 4 * 5, 2 + 4 * 5, 3 + 4 * 5

        )
        val COORD_PER_VERTEX = 3

        val COLOR_PER_VERTEX = 4

        val vertexCount = sCoor.size / COORD_PER_VERTEX
        val vertexStride = COORD_PER_VERTEX * 4
        val vertexColorStride = COLOR_PER_VERTEX * 4


    }
}

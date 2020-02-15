package com.example.opengl2.shape

import android.content.Context
import android.opengl.GLES20
import com.example.opengl2.util.GLUtil
import com.example.opengl2.util.toFloatBuffer
import java.nio.FloatBuffer

/**
 * Created by hezhanghe on 2020-02-12.
 * github: https://github.com/HadesHe
 */
class Treasure(val context: Context) {

    private var muMVPMatrixHandle: Int = 0
    private var mColorHandle: Int = 0
    private var mPositionHandle: Int = 0
    private var mProgram: Int = 0
    private var vertexBuffer: FloatBuffer?
    private var vertexCount: Int = 0

    init {
        val sCoor = gen(0.5f)

        vertexCount = sCoor.size / COORD_PER_VERTEX

        vertexBuffer = sCoor.toFloatBuffer()

        val vertexShader =
            GLUtil.loadShaderAssets(context, GLES20.GL_VERTEX_SHADER, "treasure.vert")
        val fragmentShader =
            GLUtil.loadShaderAssets(context, GLES20.GL_FRAGMENT_SHADER, "treasure.frag")
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
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount)
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mvpMatrix, 0)

        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor")
        GLES20.glUniform4fv(mColorHandle, 1, colors, 0)


        GLES20.glDisableVertexAttribArray(mPositionHandle)
//        GLES20.glDisableVertexAttribArray(mColorHandle)
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

    companion object {


        val colors = floatArrayOf(
            1f, 1f, 1f, 1f
        )
        val COORD_PER_VERTEX = 3

        val COLOR_PER_VERTEX = 4


        val vertexStride = COORD_PER_VERTEX * 4
        val vertexColorStride = COLOR_PER_VERTEX * 4


    }

}
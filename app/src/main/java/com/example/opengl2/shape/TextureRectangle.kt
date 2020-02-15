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
class TextureRectangle(val context: Context) {
    private var muMVPMatrixHandle: Int = 0
    private var mColorHandle: Int = 0
    private var mPositionHandle: Int = 0
    private var mProgram: Int = 0
    private var idBuffer: ShortBuffer? = null
    private var mTextureCooBuffer: FloatBuffer? = null
    private var vertexBuffer: FloatBuffer? = null

    init {
        bufferData()
        initProgram()
    }

    private fun initProgram() {
        val vertexShader = GLUtil.loadVertexShaderAssets(context, "rect_texture.vert")
        val fragmentShader = GLUtil.loadFragShaderAssets(context, "rect_texture.frag")

        mProgram = GLES20.glCreateProgram()
        GLES20.glAttachShader(mProgram, vertexShader)
        GLES20.glAttachShader(mProgram, fragmentShader)
        GLES20.glLinkProgram(mProgram)

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "vCoordinate")
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
    }

    fun draw(mvpMatrix: FloatArray, texId: Int) {
        GLES20.glUseProgram(mProgram)
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        GLES20.glEnableVertexAttribArray(mColorHandle)
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mvpMatrix, 0)

        GLES20.glVertexAttribPointer(
            mPositionHandle,
            COORDS_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            vertexStride,
            vertexBuffer
        )
        GLES20.glVertexAttribPointer(
            mColorHandle,
            TEXTURE_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            vertexTureStride,
            mTextureCooBuffer
        )
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId)
        GLES20.glDrawElements(
            GLES20.GL_TRIANGLES, idx.size,
            GLES20.GL_UNSIGNED_SHORT, idBuffer
        )

        GLES20.glDisableVertexAttribArray(mPositionHandle)

    }


    private fun bufferData() {
        vertexBuffer = sCoo.toFloatBuffer()
        mTextureCooBuffer = tetureCoo.toFloatBuffer()
        idBuffer = idx.toShortBuffer()

    }

    companion object {
        private val COORDS_PER_VERTEX = 3
        private val vertexStride = COORDS_PER_VERTEX * 4
        private val c = 1
        private val sCoo = floatArrayOf(
            -c.toFloat(), c.toFloat(), 0f,
            -c.toFloat(), -c.toFloat(), 0f,
            c.toFloat(), -c.toFloat(), 0f,
            c.toFloat(),c.toFloat(),0f
        )
        private val tetureCoo = floatArrayOf(
            0f, 0f,
            0f, 1f,
            1f, 0f,
            1f,1f
        )
        private val TEXTURE_PER_VERTEX = 2
        private val vertexTureStride = TEXTURE_PER_VERTEX * 4

        private val idx = shortArrayOf(
            1,2,3,0,1,3
        )
    }


}
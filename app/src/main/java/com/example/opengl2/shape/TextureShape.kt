package com.example.opengl2.shape

import android.content.Context
import android.opengl.GLES20
import com.example.opengl2.base.RenderAble
import com.example.opengl2.base.TextureShapeData
import com.example.opengl2.util.Cons
import com.example.opengl2.util.GLUtil
import com.example.opengl2.util.toFloatBuffer
import com.example.opengl2.util.toShortBuffer
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * Created by hezhanghe on 2020-02-24.
 * github: https://github.com/HadesHe
 */
class TextureShape(context: Context, var textureShapeData: TextureShapeData) :
    RenderAble(context) {

    private var muMVPMatrixHandle: Int = 0
    private var mColorHandle: Int = 0
    private var mPositionHandle: Int = 0
    private var mProgram: Int = 0
    private var mTextureBuffer: FloatBuffer?
    private var mVertexBuffer: FloatBuffer?

    init {
        mVertexBuffer = textureShapeData.mVertex.toFloatBuffer()
        mTextureBuffer = textureShapeData.mTextureArray.toFloatBuffer()
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
            Cons.DIMENSION_3 * 4,
            mVertexBuffer
        )
        GLES20.glVertexAttribPointer(
            mColorHandle,
            Cons.DIMENSION_2,
            GLES20.GL_FLOAT,
            false,
            Cons.DIMENSION_2 * 4,
            mTextureBuffer
        )
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureShapeData.mRedId)
        GLES20.glDrawArrays(
            GLES20.GL_TRIANGLES,
            0,
            textureShapeData.getCount()
        )
        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }

}
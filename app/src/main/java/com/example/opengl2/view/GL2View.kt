package com.example.opengl2.view

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.AttributeSet
import com.example.opengl2.shape.Triangle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by hezhanghe on 2020-02-11.
 * github: https://github.com/HadesHe
 */

class GL2View(context: Context, attrs: AttributeSet? = null) : GLSurfaceView(context, attrs) {
    private var mRender: Renderer

    init {
        setEGLContextClientVersion(2)
        mRender = GL2Renderer()
        setRenderer(mRender)
    }

    inner class GL2Renderer : GLSurfaceView.Renderer {

        //视图投影矩阵
        private var mMVPMatrix=FloatArray(16)
        //投影矩阵
        private var mProjectionMatrix = FloatArray(16)
        //视图矩阵
        private var mViewMatrix = FloatArray(16)
        private lateinit var mTriangle: Triangle

        override fun onDrawFrame(gl: GL10?) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)
            mTriangle.draw(mMVPMatrix)
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES20.glViewport(0, 0, width, height)
            val ratio = width / height.toFloat()
            Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
            Matrix.setLookAtM(mViewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1f, 0f)

        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES20.glClearColor(1.0f, 0f, 0f, 1.0f)
            mTriangle = Triangle(context)
        }

    }


}
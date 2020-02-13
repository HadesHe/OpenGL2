package com.example.opengl2.view

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.AttributeSet
import android.util.Log
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
        //该模式会导致 onDrawFrame 不断绘制，绘制间隔在12~50ms 之间
        mRender = GL2Renderer()
        setRenderer(mRender)
    }

    inner class GL2Renderer : GLSurfaceView.Renderer {

        private val TAG = GL2Renderer::class.java.simpleName

        private var currDeg = 30
        //视图投影矩阵
        private var mMVPMatrix = FloatArray(16)
        //投影矩阵
        private var mProjectionMatrix = FloatArray(16)
        //视图矩阵
        private var mViewMatrix = FloatArray(16)
        //变换矩阵
        private var mOpMatrix = FloatArray(16)
        private lateinit var mTriangle: Triangle

        override fun onDrawFrame(gl: GL10?) {
            Log.d(TAG, "currDeg $currDeg")
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
            Matrix.setRotateM(mOpMatrix, 0, currDeg.toFloat(), 0f, 0f, -1f)
            //通过调整在 Z 轴的位置实现越转越远的效果
            Matrix.translateM(mOpMatrix, 0, 0f, 0f, currDeg / 90f)
            Matrix.multiplyMM(
                mMVPMatrix, 0,
                mViewMatrix, 0,
                mOpMatrix, 0
            )
            Matrix.multiplyMM(
                mMVPMatrix, 0,
                mProjectionMatrix, 0,
                mMVPMatrix, 0
            )
            mTriangle.draw(mMVPMatrix)

            currDeg++
            if (currDeg >= 360) {
                currDeg = 0
            }
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
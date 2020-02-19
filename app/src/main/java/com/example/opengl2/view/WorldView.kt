package com.example.opengl2.view

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.AttributeSet
import com.example.opengl2.base.RenderAble
import com.example.opengl2.base.Shape
import com.example.opengl2.shape.SimpleShape
import com.example.opengl2.shape.WorldShape
import com.example.opengl2.util.Cons
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by hezhanghe on 2020-02-16.
 * github: https://github.com/HadesHe
 */
class WorldView(context: Context, attrs: AttributeSet? = null) : GLSurfaceView(context, attrs) {
    private var mRenderer: WorldRenderer

    init {
        setEGLContextClientVersion(2)
        mRenderer = WorldRenderer(context)
        setRenderer(mRenderer)
        renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY

    }

    inner class WorldRenderer(val context: Context) : Renderer {
        private lateinit var mWorldShape: RenderAble
        private var mProjectionMatrix: FloatArray = FloatArray(16)
        private var mViewMatrix: FloatArray = FloatArray(16)
        private var mOpMatrix: FloatArray = FloatArray(16)
        private var mMVPMatrix = FloatArray(16)
        private var currDeg = 0

        override fun onDrawFrame(gl: GL10?) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
            Matrix.setRotateM(mOpMatrix, 0, currDeg.toFloat(), 0f, 1f, 0f)
            Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mOpMatrix, 0)
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0)
            mWorldShape.draw(mMVPMatrix)
            GLES20.glEnable(GLES20.GL_DEPTH_TEST)

//            currDeg++
//            if (currDeg >= 360) {
//                currDeg = 0
//            }

        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES20.glViewport(0, 0, width, height)
            val ratio = width / height.toFloat()
            Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 9f)
            Matrix.setLookAtM(mViewMatrix, 0, 0f, 0f, 6f, 0f, 0f, 0f, 0f, 1f, 0f)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES20.glClearColor(0f, 0f, 0f, 1f)
            mWorldShape = WorldShape(context)
        }

    }
}
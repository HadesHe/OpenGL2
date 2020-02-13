package com.example.opengl2.view

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
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
        private lateinit var mTriangle: Triangle

        override fun onDrawFrame(gl: GL10?) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
            mTriangle.draw()
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES20.glViewport(0, 0, width, height)
        }

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES20.glClearColor(1.0f, 0f, 0f, 1.0f)
            mTriangle = Triangle(context)
        }

    }


}
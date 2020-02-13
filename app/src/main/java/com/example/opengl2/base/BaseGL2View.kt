package com.example.opengl2.base

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

/**
 * Created by hezhanghe on 2020-02-13.
 * github: https://github.com/HadesHe
 */
abstract class BaseGL2View(context: Context?, attrs: AttributeSet? = null) :
    GLSurfaceView(context, attrs) {

    init {
        setEGLContextClientVersion(2)
        setRenderer(getRender())
    }

    abstract fun getRender(): Renderer

}
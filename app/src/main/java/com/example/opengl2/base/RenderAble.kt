package com.example.opengl2.base

import android.content.Context

/**
 * Created by hezhanghe on 2020-02-16.
 * github: https://github.com/HadesHe
 */
abstract class RenderAble(val context: Context) {

    abstract fun draw(mvpMatrix: FloatArray)

}
package com.example.opengl2

import android.view.View
import com.example.opengl2.base.BaseActivity
import com.example.opengl2.view.TextureTriangleView

/**
 * Created by hezhanghe on 2020-02-14.
 * github: https://github.com/HadesHe
 */
class TextureTriangleActivity : BaseActivity() {
    override fun createView(): View {
        return TextureTriangleView(this)
    }
}
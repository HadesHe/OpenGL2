package com.example.opengl2.activity

import android.view.View
import com.example.opengl2.base.BaseActivity
import com.example.opengl2.view.WorldView

/**
 * Created by hezhanghe on 2020-02-20.
 * github: https://github.com/HadesHe
 */
class RingActivity : BaseActivity() {
    override fun createView(): View {
        return WorldView(this, null, "Ring")
    }
}
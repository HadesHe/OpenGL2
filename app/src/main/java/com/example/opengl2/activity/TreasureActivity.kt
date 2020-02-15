package com.example.opengl2.activity

import android.view.View
import com.example.opengl2.base.BaseActivity
import com.example.opengl2.view.TreasureView

/**
 * Created by hezhanghe on 2020-02-15.
 * github: https://github.com/HadesHe
 */
class TreasureActivity : BaseActivity() {
    override fun createView(): View {
        return TreasureView(this)
    }

}
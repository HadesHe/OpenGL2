package com.example.opengl2

import android.view.View
import com.example.opengl2.base.BaseActivity
import com.example.opengl2.view.HexagonView

/**
 * Created by hezhanghe on 2020-02-13.
 * github: https://github.com/HadesHe
 */
class HexagonActivity:BaseActivity() {
    override fun createView(): View {
        return HexagonView(this);
    }
}
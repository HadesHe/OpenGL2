package com.example.opengl2.shape

import android.content.Context
import android.opengl.GLES20
import com.example.opengl2.base.RenderAble
import com.example.opengl2.base.OP
import com.example.opengl2.base.Shape
import com.example.opengl2.util.Cons
import com.example.opengl2.util.GLUtil
import com.example.opengl2.util.toFloatBuffer
import java.nio.FloatBuffer
import java.util.ArrayList

class WorldShape(context: Context) : RenderAble(context), OP<RenderAble> {
    override fun add(ts: RenderAble) {
        mRendererAbles.add(ts)
    }

    override fun remove(id: Int) {
        if (id >= mRendererAbles.size) {
            return
        }
        mRendererAbles.removeAt(id)
    }

    private var mRendererAbles: ArrayList<RenderAble>

    init {
        mRendererAbles = arrayListOf<RenderAble>()
        var coo = Shape(Cons.VERTEX_COO, Cons.COLOR_COO, GLES20.GL_LINES)
        var ground = Shape(mVertex, mColor, GLES20.GL_LINE_LOOP)
        var top = ground.moveAndCreate(0f, 1f, 0f)
        var bottom = ground.moveAndCreate(0f, -1f, 0f)

        add(SimpleShape(context, coo))
        add(SimpleShape(context, top))
        add(SimpleShape(context, bottom))
        add(SimpleShape(context, ground))
    }


    override fun draw(mvpMatrix: FloatArray) {
        mRendererAbles.forEach {
            it.draw(mvpMatrix)
        }
    }

    companion object {
        val mVertex = floatArrayOf(
            -1f, 0f, -1f,
            -1f, 0f, 1f,
            1f, 0f, 1f,
            1f, 0f, -1f
        )
        val mColor = floatArrayOf(
            1f, 1f, 1f, 1f,
            1f, 1f, 1f, 1f,
            1f, 1f, 1f, 1f,
            0.21960784f, 0.56078434f, 0.92156863f, 1f
        )
    }

}

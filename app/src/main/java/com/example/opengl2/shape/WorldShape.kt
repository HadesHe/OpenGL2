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


//          宝石团
//        var distance=0.6f
//        var treasure = Shape(Cons.TREASURE_COO, Cons.TREASURE_COLOR, GLES20.GL_TRIANGLE_FAN)
//        var treasure1=treasure.moveAndCreate(distance,0f,0f)
//        var treasure2=treasure.moveAndCreate(-distance,0f,0f)
//
//        var treasure23=treasure.moveAndCreate(0f,-distance,0f)
//        var treasure22=treasure23.moveAndCreate(-distance,0f,0f)
//        var treasure21=treasure22.moveAndCreate(-distance,0f,0f)
//        var treasure24=treasure23.moveAndCreate(distance,0f,0f)
//        var treasure25=treasure24.moveAndCreate(distance,0f,0f)
//
//        var treasure32=treasure23.moveAndCreate(0f,-distance,0f)
//        var treasure31=treasure32.moveAndCreate(-distance,0f,0f)
//        var treasure33=treasure32.moveAndCreate(distance,0f,0f)
//        var treasure4=treasure32.moveAndCreate(0f,-distance,0f)
//
//        add(SimpleShape(context,treasure))
//        add(SimpleShape(context,treasure1))
//        add(SimpleShape(context,treasure2))
//        add(SimpleShape(context,treasure21))
//        add(SimpleShape(context,treasure22))
//        add(SimpleShape(context,treasure23))
//        add(SimpleShape(context,treasure24))
//        add(SimpleShape(context,treasure25))
//
//        add(SimpleShape(context,treasure31))
//        add(SimpleShape(context,treasure32))
//        add(SimpleShape(context,treasure33))
//
//        add(SimpleShape(context,treasure4))
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

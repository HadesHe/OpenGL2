package com.example.opengl2.util

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import java.nio.charset.Charset

object GLUtil {

    private val TAG = GLUtil::class.java.simpleName

    /**
     *  加载着色器
     *  type : 顶点着色器{@link GLE20.GL_VERTEX_SHADER}
     *          片着色器{@link GLE20.GL_FRAGMENT_SHADER}
     */
    fun loadShader(type: Int, shaderCode: String?): Int {
        val shader = GLES20.glCreateShader(type)
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        return checkCompile(type, shader)
    }

    private fun checkCompile(type: Int, shader: Int): Int {
        val compiler = IntArray(1)
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiler, 0)
        if (compiler[0] == 0) {
            Log.e(TAG, "Could not compile shade  $type : ${GLES20.glGetShaderInfoLog(shader)}")
            GLES20.glDeleteShader(shader)
            return 0
        }
        return shader
    }

    /**
     * 从 asset 中加载 shader 内容
     */

    fun loadShaderAssets(ctx: Context, type: Int, name: String): Int {
        var result: String? = null
        try {
            val inputStream = ctx.assets.open(name)
            var buff = inputStream.readBytes()
            inputStream.close()
            result = String(buff, Charset.forName("UTF-8"))
            result = result.replace("\\r\\n", "\n");
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return loadShader(type, result)

    }

}

/**
 * float 数组缓冲数据
 */
fun FloatArray.getFloatBuffer(): FloatBuffer? {
    if (isEmpty()) {
        return null
    }
    var buffer = ByteBuffer.allocateDirect(size * 4)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer()
    buffer.put(this)
    buffer.position(0)
    return buffer
}

/**
 * short 数组缓冲数据
 */
fun ShortArray.getShortBuffer(): ShortBuffer? {
    if (isEmpty()) {
        return null
    }
    var buffer = ByteBuffer.allocateDirect(size * 2)
        .order(ByteOrder.nativeOrder()).asShortBuffer()
    buffer.put(this)
    buffer.position(0)
    return buffer
}

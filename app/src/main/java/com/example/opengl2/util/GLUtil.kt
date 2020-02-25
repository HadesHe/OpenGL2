package com.example.opengl2.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils
import android.util.Log
import com.example.opengl2.RepeatType
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

    fun loadVertexShaderAssets(ctx: Context, name: String): Int {
        return loadShaderAssets(ctx, GLES20.GL_VERTEX_SHADER, name)
    }

    fun loadFragShaderAssets(ctx: Context, name: String): Int {
        return loadShaderAssets(ctx, GLES20.GL_FRAGMENT_SHADER, name)
    }


    fun loadTexture(ctx: Context, resId: Int): Int {
        val bitmap = BitmapFactory.decodeResource(ctx.resources, resId)
        return loadTexture(bitmap)
    }

    fun loadTexture(bitmap: Bitmap, repeatType: RepeatType = RepeatType.REPEAT): Int {
        val textures = IntArray(1)
        GLES20.glGenTextures(1, textures, 0)
        var textureId = textures[0]
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        GLES20.glTexParameterf(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MIN_FILTER,
            GLES20.GL_NEAREST.toFloat()
        )
        GLES20.glTexParameterf(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MAG_FILTER,
            GLES20.GL_LINEAR.toFloat()
        )

        var wrapS = GLES20.GL_REPEAT
        var wrapT = GLES20.GL_REPEAT
        when (repeatType) {
            RepeatType.NONE -> {
                wrapS = GLES20.GL_CLAMP_TO_EDGE
                wrapT = GLES20.GL_CLAMP_TO_EDGE

            }
            RepeatType.REPAET_X -> {
                wrapS = GLES20.GL_REPEAT
                wrapT = GLES20.GL_CLAMP_TO_EDGE

            }
            RepeatType.REPEAT_Y -> {
                wrapS = GLES20.GL_CLAMP_TO_EDGE
                wrapT = GLES20.GL_REPEAT

            }
            RepeatType.REPEAT -> {
                wrapS = GLES20.GL_REPEAT
                wrapT = GLES20.GL_REPEAT

            }
        }
        GLES20.glTexParameterf(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_WRAP_S,
            wrapS.toFloat()
        )
        GLES20.glTexParameterf(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_WRAP_T,
            wrapT.toFloat()
        )
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
        bitmap.recycle()
        return textureId

    }

}

/**
 * float 数组缓冲数据
 */
fun FloatArray.toFloatBuffer(): FloatBuffer? {
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
fun ShortArray.toShortBuffer(): ShortBuffer? {
    if (isEmpty()) {
        return null
    }
    var buffer = ByteBuffer.allocateDirect(size * 2)
        .order(ByteOrder.nativeOrder()).asShortBuffer()
    buffer.put(this)
    buffer.position(0)
    return buffer
}

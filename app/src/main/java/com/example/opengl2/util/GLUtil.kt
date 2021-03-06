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
import java.io.BufferedReader
import java.io.InputStreamReader


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

    //-------------加载obj点集----------------
    //从obj文件中加载仅携带顶点信息的物体
    fun loadPosInObj(name: String, ctx: Context): FloatArray {
        val alv = ArrayList<Float>()//原始顶点坐标列表
        val alvResult = ArrayList<Float>()//结果顶点坐标列表
        try {
            val `in` = ctx.assets.open(name)
            val isr = InputStreamReader(`in`)
            val br = BufferedReader(isr)

            br.readLines().forEach {

                var tempsa = it.split(" ")
                if (tempsa[0].trim().equals("v")) {//顶点坐标

//                    Log.d("GLUtil","tempsa 1=${tempsa[1]} 2=${tempsa[2]} 3=${tempsa[3]} 3=${tempsa[4]}")
                    alv.add(tempsa[2].toFloat())
                    alv.add(tempsa[3].toFloat())
                    alv.add(tempsa[4].toFloat())

                }else if(tempsa[0].trim().equals("f")){//此行为三角形面
//                    Log.d("GLUtil","tempsaf 1=${tempsa[1]} 2=${tempsa[2]} 3=${tempsa[3]} 3=${tempsa[4]}")

                    var index=tempsa[1].split("/")[0].toInt()-1
                    alvResult.add(alv.get(3*index))
                    alvResult.add(alv.get(3*index+1))
                    alvResult.add(alv.get(3*index+2))

                    index = Integer.parseInt(tempsa[2].split("/")[0]) - 1;
                    alvResult.add(alv.get(3 * index));
                    alvResult.add(alv.get(3 * index + 1));
                    alvResult.add(alv.get(3 * index + 2));
                    index = Integer.parseInt(tempsa[3].split("/")[0]) - 1;
                    alvResult.add(alv.get(3 * index));
                    alvResult.add(alv.get(3 * index + 1));
                    alvResult.add(alv.get(3 * index + 2));


                }
            }

        } catch (e: Exception) {
            Log.d("load error", "load error")
            e.printStackTrace()
        }

        //生成顶点数组
        val size = alvResult.size
        val vXYZ = FloatArray(size)
        for (i in 0 until size) {
            vXYZ[i] = alvResult.get(i)
        }
        return vXYZ
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

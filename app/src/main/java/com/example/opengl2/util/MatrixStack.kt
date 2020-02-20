package com.example.opengl2.util

import android.opengl.Matrix

/**
 * Created by hezhanghe on 2020-02-19.
 * github: https://github.com/HadesHe
 */
object MatrixStack {

    //投影矩阵
    private var mProjectionMatrix = FloatArray(16)
    //相机矩阵
    private var mViewMatrix = FloatArray(16)
    //变换矩阵
    private var mOpMatrix = floatArrayOf(
        1f, 0f, 0f, 0f,
        0f, 1f, 0f, 0f,
        0f, 0f, 1f, 0f,
        0f, 0f, 0f, 1f
    )

    //总变换矩阵
    private var mMVPMatrix = FloatArray(16)

    private var stackTop = -1
    private var mStack = Array(10) {
        floatArrayOf(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
        )
    }

    fun save() {
        stackTop++
        System.arraycopy(
            mOpMatrix, 0,
            mStack[stackTop],
            0, 16
        )
    }

    fun restore() {
        System.arraycopy(
            mStack[stackTop], 0,
            mOpMatrix, 0, 16
        )
        stackTop--
    }

    fun reset() {
        mOpMatrix = floatArrayOf(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f
        )
    }


    fun translate(x: Float, y: Float, z: Float) {
        Matrix.translateM(mOpMatrix, 0, x, y, z)

    }

    fun rotate(deg: Float, x: Float, y: Float, z: Float) {
        Matrix.rotateM(mOpMatrix, 0, deg, x, y, z)
    }

    fun scale(x: Float, y: Float, z: Float) {
        Matrix.scaleM(mOpMatrix, 0, x, y, z)
    }

    //    相机位置
    fun lookAt(
        cx: Float, cy: Float, cz: Float,
        tx: Float, ty: Float, tz: Float,
        upx: Float, upy: Float, upz: Float
    ) {
        Matrix.setLookAtM(
            mViewMatrix, 0,
            cx, cy, cz,
            tx, ty, tz,
            upx, upy, upz
        )

    }

    //    透视投影参数
    fun frustum(
        left: Float, right: Float, bottom: Float,
        top: Float, near: Float, far: Float
    ) {
        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far)
    }

    //    栈顶的变换矩阵
    fun peek(): FloatArray {
        submit()
        return mMVPMatrix
    }

    private fun submit() {
        Matrix.multiplyMM(
            mMVPMatrix, 0,
            mViewMatrix, 0,
            mOpMatrix, 0
        )
        Matrix.multiplyMM(
            mMVPMatrix, 0,
            mProjectionMatrix, 0,
            mMVPMatrix, 0
        )
    }

    //    变换矩阵
    fun getOpMatrix(): FloatArray {
        return mOpMatrix
    }

}
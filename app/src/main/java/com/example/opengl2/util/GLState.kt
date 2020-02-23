package com.example.opengl2.util

import java.nio.FloatBuffer

object GLState {

    private var lightLocation = floatArrayOf(0f, 0f, 0f)

    public var lightPositionFB: FloatBuffer? = null

    fun setLightLocation(x: Float, y: Float, z: Float) {
        lightLocation[0] = x
        lightLocation[1] = y
        lightLocation[2] = z
        lightPositionFB = lightLocation.toFloatBuffer()

    }

}

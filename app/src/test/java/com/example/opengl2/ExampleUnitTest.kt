package com.example.opengl2

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        for (i in 180..270) {

            var x = 1 + Math.sin(Math.PI * i / 180f)
            var y = 1 + Math.cos(Math.PI * i / 180f)

            System.out.println("x=$x y=$y")

        }
    }
}

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
        Test("a")
        Test("a1")
        Test("a2")
        Test("a3")
        Test("a4")


    }


}

class Test(val a:String){
    init {
        System.out.println("a $a")
    }
}

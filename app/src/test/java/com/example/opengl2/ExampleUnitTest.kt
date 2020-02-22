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


    }


}

fun maxProfit(prices: IntArray): Int {
    if (prices.isEmpty() || prices.size == 1) {
        return 0
    }

    var i = 0
    var valley = prices[0]
    var peak = prices[0]
    var maxprofit = 0
    while (i < prices.size) {
        while (i < prices.size - 1 && prices[i] >= prices[i + 1]) {
            i++
        }
        valley = prices[i]
        while ((i < prices.size - 1 && prices[i] <= prices[i + 1])) {
            i++
        }
        peak = prices[i]
        maxprofit += peak - valley
    }
    return maxprofit


}



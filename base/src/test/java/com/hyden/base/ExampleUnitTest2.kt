package com.hyden.base

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest2 {
    @Test
    fun addition_isCorrect() {
        var answer = 0
        val scoville = arrayListOf<Int>(1,2,3,9,10,11)
        val K = 7

        for (i in 0 until scoville.size) {
            scoville.sort()
            val mixScoville = scovilleCalcurator(scoville[0],scoville[1])
            scoville.removeAt(0)
            scoville[0] = mixScoville
            println(scoville)
            answer++
            if(mixScoville > K) {
                println(answer)
                break
            }
        }


        println("end")
    }

    fun scovilleCalcurator(firstMin : Int , secondMin : Int) : Int{
        return firstMin + (secondMin * 2)
    }
}

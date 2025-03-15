package it.polito.group9

import kotlin.test.Test
import kotlin.test.assertEquals

class MainTest {
    @Test
    fun additionShouldBeCorrect() {
        val sum = 2 + 3
        assertEquals(5, sum, "2 + 3 should equal 5")
    }
}
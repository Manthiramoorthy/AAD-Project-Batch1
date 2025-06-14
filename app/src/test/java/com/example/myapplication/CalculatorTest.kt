package com.example.myapplication

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CalculatorTest {
    lateinit var calculator: Calculator
    @Before
    fun before() {
        calculator = Calculator()
    }

    @Test
    fun `Add two number`() {
        val a = 10
        val b = 20
        val add = a + b
        val result = calculator.add(a, b)
        Assert.assertSame(add, result)
    }

//    @Test
//    fun `Divide two number`() {
//        val a = 10
//        val b = 20
//        val divide = a / b
//        val result = calculator.divide(a, b)
//        Assert.assertSame(divide, result)
//    }
}
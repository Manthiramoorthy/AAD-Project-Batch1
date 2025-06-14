package com.example.myapplication

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CalculatorTest {
    lateinit var calculator: Calculator
    @Before
    fun setup() {
        calculator = Calculator()
    }

    @Test
    fun `Given call add method with two number Then return addition of two number`() {
        val a = 10
        val b = 20
        val expected = a + b
        val result = calculator.add(a, b)
        Assert.assertSame(expected, result)
    }

    @Test
    fun `Given call divide method two number Then return divide of two number when b is valid`() {
        val a = 10
        val b = 20
        val expected = a / b
        val result = calculator.divide(a, b)
        Assert.assertSame(expected, result)
    }

    @Test
    fun `Given call divide method two number Then return null when b is 0`() {
        val a = 10
        val b = 0
        val expected = null
        val result = calculator.divide(a, b)
        Assert.assertSame(expected, result)
    }

}
package com.example.myapplication

class Calculator {
    fun add(a: Int, b: Int): Int = a + b
    fun divide(a: Int, b: Int): Int? {
        if (b == 0) {
            return null
        }
        return a / b
    }
}
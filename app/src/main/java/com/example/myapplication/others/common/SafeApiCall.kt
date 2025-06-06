package com.example.myapplication.others.common

import com.google.gson.JsonParseException
import java.io.IOException
import kotlin.Result

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T // generic, higher order function
): ResultWrapper<T> {
    return try {
        val response = apiCall.invoke()
        ResultWrapper.Success(response)
    } catch (e: IOException) {
        ResultWrapper.Failure("Network error. please, check your network")
    } catch (e:JsonParseException) {
        ResultWrapper.Failure("Invalid response")
    } catch (e: Exception) {
        ResultWrapper.Failure("unExpected error, please contact admin")
    }
}

sealed class ResultWrapper<out T> { // out invariant, sealed class
    data class Success<out T>(val data: T): ResultWrapper<T>()
    data class Failure(val message: String): ResultWrapper<Nothing>()
}
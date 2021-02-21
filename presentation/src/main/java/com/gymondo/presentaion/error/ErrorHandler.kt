package com.gymondo.presentaion.error

interface ErrorHandler {
    fun getErrorMessage(error: Throwable): String
}
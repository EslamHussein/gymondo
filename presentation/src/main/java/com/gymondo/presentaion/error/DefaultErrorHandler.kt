package com.gymondo.presentaion.error

import android.content.res.Resources
import com.gymondo.presentaion.R
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class DefaultErrorHandler constructor(private val resourceManager: Resources) :
    ErrorHandler {
    override fun getErrorMessage(error: Throwable): String {

        return when (error) {
            is IOException, is UnknownHostException, is SocketException
            -> resourceManager.getString(R.string.no_internet_connection)
            is SocketTimeoutException -> {
                resourceManager.getString(R.string.timeout_error_message)
            }
            else -> resourceManager.getString(R.string.unknown_error)
        }
    }

}
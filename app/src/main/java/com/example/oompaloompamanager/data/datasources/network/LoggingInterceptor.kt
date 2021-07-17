package com.example.oompaloompamanager.data.datasources.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.*

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val startTime = System.currentTimeMillis()
        val requestLogMessage = buildLog(request = request, startTime = startTime)
        Log.d("LOG REQUEST", requestLogMessage)

        val response = chain.proceed(request)

        val endTime = System.currentTimeMillis()
        val responseLogMessage = buildLog(request, response, startTime, endTime)
        Log.d("LOG RESPONSE", responseLogMessage)

        return response
    }

    private fun buildLog(
        request: Request,
        response: Response? = null,
        startTime: Long,
        endTime: Long? = null
    ): String {
        var logMessage =
            response?.let { " \n==================== R E S P O N S E ====================" }
                ?: let { " \n===================== R E Q U E S T =====================" }
        logMessage += "\nURL: ${request.url()}"
        logMessage += "\nHEADERS: ${request.headers().toMultimap()}"
        logMessage += "\nDATETIME: ${
            SimpleDateFormat("MMM dd,yyyy HH:mm:ss:SSSS").format(
                Date(
                    endTime ?: startTime
                )
            )
        }"
        endTime?.apply {
            logMessage += "\nDURATION: ${endTime - startTime} ms"
        }
        response?.apply {
            response.body()?.apply {
                val responseBody = response.peekBody(1048576L).string()
                logMessage += "\nBODY: $responseBody"
            }
        } ?: apply {
            request.body()?.apply {
                val requestBody = requestBodyToString(request)
                logMessage += "\nBODY: $requestBody"
            }
        }
        logMessage += "\n======================================================="
        return logMessage
    }

    private fun requestBodyToString(request: Request): String {
        val buffer = okio.Buffer()
        request.body()?.writeTo(buffer)
        return buffer.readUtf8()
    }
}
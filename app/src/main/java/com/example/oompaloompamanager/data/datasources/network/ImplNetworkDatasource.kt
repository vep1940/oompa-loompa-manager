package com.example.oompaloompamanager.data.datasources.network

import com.example.oompaloompamanager.BuildConfig
import com.example.oompaloompamanager.commons.AppResponse
import com.example.oompaloompamanager.data.datasources.DataSource
import com.example.oompaloompamanager.data.datasources.network.services.ApiService
import com.example.oompaloompamanager.data.models.OompaLoompaDetailResponse
import com.example.oompaloompamanager.data.models.WorkerPageResponse
import com.example.oompaloompamanager.domain.constants.AppError
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import javax.inject.Inject

class ImplNetworkDatasource @Inject constructor() : DataSource.Network {

    companion object {
        private val apiService: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl("https://2q2woep105.execute-api.eu-west-1.amazonaws.com/napptilus/")
                .addConverterFactory(MoshiConverterFactory.create())
                .apply {
                    if (BuildConfig.DEBUG) {
                        client(OkHttpClient.Builder().addInterceptor(LoggingInterceptor()).build())
                    }
                }.build()
                .create(ApiService::class.java)
        }
    }

    override suspend fun getWorkers(page: Int): AppResponse<WorkerPageResponse> {
        return safeRequest { apiService.getWorkers(page) }
    }

    override suspend fun getWorkerDetail(id: Int): AppResponse<OompaLoompaDetailResponse> {
        return safeRequest { apiService.getWorker(id) }
    }

    private suspend fun <T> safeRequest(requestLambda: suspend () -> Response<T>): AppResponse<T> {
        return try {
            val response = requestLambda()
            if (response.isSuccessful) {
                response.body()?.let { responseBody ->
                    AppResponse.ResponseOk(responseBody)
                } ?: let {
                    AppResponse.ResponseKo(AppError.EMPTY_BODY_RESPONSE_ERROR)
                }
            } else {
                AppResponse.ResponseKo(AppError.UNSUCCESSFUL_REQUEST_ERROR)
            }
        } catch (ex: HttpException) {
            AppResponse.ResponseKo(AppError.HTTP_ERROR)
        } catch (ex: IOException) {
            AppResponse.ResponseKo(AppError.NO_INTERNET_ERROR)
        } catch (ex: Exception) {
            AppResponse.ResponseKo(AppError.UNKNOWN_ERROR)
        }
    }
}
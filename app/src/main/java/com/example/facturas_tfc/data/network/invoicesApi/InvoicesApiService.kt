package com.example.facturas.data.network.invoicesApi

import com.example.facturas.data.network.invoicesApi.models.InvoicesListResponse
import com.example.facturas.utils.BASE_URL
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface IInvoicesApi {
    suspend fun getAllInvoices(): Response<InvoicesListResponse>
}

interface IInvoicesProdApi : IInvoicesApi {
    @GET("facturas")
    override suspend fun getAllInvoices(): Response<InvoicesListResponse>
}

/**
 * This class is responsible for interacting with REST HTTP URL
 */
class InvoicesApiService private constructor() {

    companion object {
        private var _INSTANCE: InvoicesApiService? = null
        lateinit var retrofit: IInvoicesProdApi

        fun getInstance(): InvoicesApiService {
            return if (_INSTANCE == null) {
                // Instantiate retrofit
                val retrofitInstance = getRetrofitInstance()
                retrofit = retrofitInstance.create(IInvoicesProdApi::class.java)
                InvoicesApiService()
            } else {
                requireNotNull(_INSTANCE)
            }
        }

        private fun getRetrofitInstance(): Retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    suspend fun getAllInvoices(): Response<InvoicesListResponse> {
        return retrofit.getAllInvoices()
    }
}
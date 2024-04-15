package com.example.facturas_tfc.data.network.invoicesApi

import android.util.Log
import co.infinum.retromock.NonEmptyBodyFactory
import co.infinum.retromock.Retromock
import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockBehavior
import co.infinum.retromock.meta.MockCircular
import co.infinum.retromock.meta.MockResponse
import co.infinum.retromock.meta.MockResponses
import com.example.facturas_tfc.data.network.invoicesApi.models.InvoicesListResponse
import com.example.facturas_tfc.utils.AppEnvironment
import com.example.facturas_tfc.utils.BASE_URL
import com.example.facturas_tfc.utils.ResourceBodyFactory
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

interface IInvoicesMockApi : IInvoicesApi {
    @Mock
    @MockResponses(
        MockResponse(body = "mock_empty_list.json"),
        MockResponse(body = "mock_invoices_list.json"),
        MockResponse(body = "mock_invoices_current_list.json"),
        MockResponse(body = "mock_invoices_paid_list.json")
    )
    @MockCircular
    @MockBehavior(durationDeviation = 1000, durationMillis = 1000)
    @GET("/")
    override suspend fun getAllInvoices(): Response<InvoicesListResponse>
}

/**
 * This class is responsible for interacting with REST HTTP URL
 */
class InvoicesApiService private constructor() {

    companion object {
        private const val TAG = "VIEWNEXT InvoicesApiService"

        private var _INSTANCE: InvoicesApiService? = null
        lateinit var retrofit: IInvoicesProdApi
        lateinit var retromock: IInvoicesMockApi

        fun getInstance(): InvoicesApiService {
            return if (_INSTANCE == null) {
                // Instantiate retrofit
                val _retrofit = getRetrofitInstance()
                // Instantiate retromock
                val _retromock = getRetromockInstance()
                retrofit = _retrofit.create(IInvoicesProdApi::class.java)
                retromock = _retromock.create(IInvoicesMockApi::class.java)
                InvoicesApiService()
            } else {
                requireNotNull(_INSTANCE)
            }
        }

        private fun getRetrofitInstance(): Retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()

        private fun getRetromockInstance(): Retromock =
            Retromock.Builder().retrofit(getRetrofitInstance())
                .defaultBodyFactory(NonEmptyBodyFactory(ResourceBodyFactory())).build()
    }

    suspend fun getAllInvoices(environment: String): Response<InvoicesListResponse> {
        return if (environment == AppEnvironment.MOCK_ENVIRONMENT) {
            Log.d(TAG, "Fetching invoices from retromock...")
            retromock.getAllInvoices()
        } else {
            Log.d(TAG, "Fetching invoices from retrofit...")
            retrofit.getAllInvoices()
        }
    }
}
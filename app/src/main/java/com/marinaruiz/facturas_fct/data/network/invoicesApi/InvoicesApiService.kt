package com.marinaruiz.facturas_fct.data.network.invoicesApi

import android.util.Log
import co.infinum.retromock.NonEmptyBodyFactory
import co.infinum.retromock.Retromock
import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockBehavior
import co.infinum.retromock.meta.MockCircular
import co.infinum.retromock.meta.MockResponse
import co.infinum.retromock.meta.MockResponses
import com.marinaruiz.facturas_fct.data.network.invoicesApi.models.InvoicesListResponse
import com.marinaruiz.facturas_fct.core.utils.AppEnvironment
import com.marinaruiz.facturas_fct.core.utils.BASE_URL
import com.marinaruiz.facturas_fct.data.network.invoicesApi.models.SSDetailResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface IInvoicesApi {
    suspend fun getAllInvoices(): Response<InvoicesListResponse>
    suspend fun getSmartSolarDetails(): Response<SSDetailResponse>
}

interface IInvoicesProdApi : IInvoicesApi {
    @GET("facturas")
    override suspend fun getAllInvoices(): Response<InvoicesListResponse>

    @GET("smart-solar")
    override suspend fun getSmartSolarDetails(): Response<SSDetailResponse>
}

interface IInvoicesMockApi : IInvoicesApi {
    @Mock
    @MockResponses(
        MockResponse(body = "mock_invoices_list.json"),
        MockResponse(body = "mock_empty_list.json"),
        MockResponse(body = "mock_invoices_current_list.json"),
        MockResponse(body = "mock_invoices_paid_list.json")
    )
    @MockCircular
    @MockBehavior(durationDeviation = 1000, durationMillis = 1000)
    @GET("/")
    override suspend fun getAllInvoices(): Response<InvoicesListResponse>

    @Mock
    @MockResponse(body = "details_mock.json")
    @MockBehavior(durationDeviation = 1000, durationMillis = 1000)
    @GET("/")
    override suspend fun getSmartSolarDetails(): Response<SSDetailResponse>
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
                InvoicesApiService().also { apiSvc -> _INSTANCE = apiSvc }
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

    suspend fun fetchAllInvoicesFromApi(environment: String): Response<InvoicesListResponse> {
        return if (environment == AppEnvironment.MOCK_ENVIRONMENT) {
            Log.d(TAG, "Fetching invoices from retromock...")
            retromock.getAllInvoices()
        } else {
            Log.d(TAG, "Fetching invoices from retrofit...")
            retrofit.getAllInvoices()
        }
    }

    suspend fun fetchSmartSolarDetailsFromApi(environment: String): Response<SSDetailResponse> {
        return if (environment == AppEnvironment.MOCK_ENVIRONMENT) {
            Log.d(TAG, "Fetching details from retromock...")
            retromock.getSmartSolarDetails()
        } else {
            Log.d(TAG, "Fetching details from retrofit...")
            retrofit.getSmartSolarDetails()
        }
    }
}
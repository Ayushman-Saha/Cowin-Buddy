package com.ayushman.vaccinenotifier.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

//The base url for admin API
private const val BASE_URL = "https://cdn-api.co-vin.in/api/v2/admin/location/"

//The moshi adapter for parsing JSON
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//The interface for all Admin API calls
interface AdminApiService {
    @Headers("user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
    @GET("states")
    fun getStateListAsync() : Deferred<NetworkStateContainer>

    @Headers("user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
    @GET("districts/{state_id}")
    fun getDistrictListAsync(@Path(value = "state_id") stateId: Int) : Deferred<NetworkDistrictContainer>
}


//Exposing the services using object
object AdminApi {
    //The retrofit object
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val adminService: AdminApiService =
        retrofit.create(AdminApiService::class.java)
}
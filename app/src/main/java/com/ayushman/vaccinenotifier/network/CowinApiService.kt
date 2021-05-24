package com.ayushman.vaccinenotifier.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


//The base url for cowin API
private const val BASE_URL = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/"

//The moshi adapter for parsing JSON
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//The interface for all Cowin API calls
interface CowinApiService {
    @Headers("user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
    @GET("findByDistrict")
    fun getScheduleListAsync(@Query("district_id") id : Int, @Query("date") date : String) : Deferred<NetworkScheduleContainer>
}

//Exposing the services using object
object CowinApi {
    //The retrofit object
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val cowinService: CowinApiService =
        retrofit.create(CowinApiService::class.java)
}
package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val asteroidListRetrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

private val pictureOfDayRetrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

interface AsteroidListApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidList(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): String
}

interface PictureOfDayApiService {
    @GET("planetary/apod?api_key=${Constants.API_KEY}")
    suspend fun getPictureOfDay(): PictureOfDayNetwork
}


object NasaApi {
    val asteroidListRetrofitService: AsteroidListApiService by lazy {
        asteroidListRetrofit.create(AsteroidListApiService::class.java)
    }

    val pictureOfDayRetrofitService: PictureOfDayApiService by lazy {
        pictureOfDayRetrofit.create(PictureOfDayApiService::class.java)
    }
}

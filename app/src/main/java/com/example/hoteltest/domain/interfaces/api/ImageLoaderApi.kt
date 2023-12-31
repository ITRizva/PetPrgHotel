package com.example.hoteltest.domain.interfaces.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ImageLoaderApi {

    @Streaming
    @GET
    suspend fun getImage(@Url imageUrl:String):Response<ResponseBody>
}
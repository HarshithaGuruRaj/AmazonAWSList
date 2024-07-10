package com.example.fetchawslist

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
//
//    private val retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://fetch-hiring.s3.amazonaws.com")
//            .addConvertorFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val apiInterface by lazy {
//       retrofit.create(ApiInterface::class.java)
//    }
    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

    val apiInterface: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }


}
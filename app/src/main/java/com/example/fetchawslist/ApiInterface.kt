package com.example.fetchawslist

import android.content.ClipData
import retrofit2.Call
import retrofit2.http.GET


interface ApiInterface {
    @GET("hiring.json")
    fun getListItems(): Call<List<ResponseDataListItem>>

}
package com.mohammadwiram.wiraandroiduas.retrofit

import com.mohammadwiram.wiraandroiduas.MainModel
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {

    @GET("data.php")
    fun data(): Call<MainModel>
}
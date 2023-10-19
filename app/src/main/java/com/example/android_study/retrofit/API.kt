package com.example.android_study.retrofit

import retrofit2.Call
import retrofit2.http.*

interface API {
    @GET("/posts")
    fun getData(@Query("userId") id: String): Call<List<Post>>

    @FormUrlEncoded
    @POST("/posts")
    fun getPostList(@FieldMap param: HashMap<String, Any>): Call<Post>

    @FormUrlEncoded
    @PUT("/posts/{id}")
    fun putPost(@Path("id") id: Int, @FieldMap param: HashMap<String, Any>): Call<Post>

    @DELETE("/posts/{id}")
    fun deletePost(@Path("id") id: Int): Call<Void>
}
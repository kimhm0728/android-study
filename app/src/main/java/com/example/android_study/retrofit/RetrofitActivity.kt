package com.example.android_study.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.android_study.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        RetrofitBuilder.api.getData("1").enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    Log.e(TAG, "getData onResponse()")
                    response.body()?.get(3)?.let { Log.e(TAG, it.title) }
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e(TAG, t.localizedMessage)
            }
        })

        val input = HashMap<String, Any>()
        input["userId"] = 1
        input["title"] = "타이틀"
        input["body"] = "바디"
        RetrofitBuilder.api.getPostList(input).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    Log.e(TAG, "getPostList onResponse()")
                    Log.e(TAG, "${response.body()?.title}")
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e(TAG, t.localizedMessage)
            }
        })

        val map = HashMap<String, Any>()
        map["title"] = "타이틀"
        map["userId"] = 3

        RetrofitBuilder.api.putPost(3, map).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    Log.e(TAG, "putPost onResponse()")

                    val body = response.body()
                    Log.e(TAG, "userId: ${body?.userId}")
                    Log.e(TAG, "id: ${body?.id}")
                    Log.e(TAG, "title: ${body?.title}")
                    Log.e(TAG, "body: ${body?.body}")
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e(TAG, t.localizedMessage)
            }
        })

        RetrofitBuilder.api.deletePost(5).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.e(TAG, "deletePost onResponse()")
                    Log.e(TAG, "response code : ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, t.localizedMessage)
            }
        })

    }

    companion object {
        val TAG = RetrofitActivity::class.simpleName
    }
}
package com.codesample.retrofitsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
                .baseUrl("http://10.80.163.40:4000/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        val service = retrofit.create(RetrofitNetwork::class.java)

        service.postLogin(ReqLoginData("skehdgur8591", "2345"))?.enqueue(object : Callback<Response<ResLoginData>> {

            override fun onFailure(call: Call<Response<ResLoginData>>, t: Throwable) {
            }

            override fun onResponse(call: Call<Response<ResLoginData>>, response: retrofit2.Response<Response<ResLoginData>>) {
                Log.d("Response :: ", response?.body().toString())
            }
        })
    }
}
data class Response<T>(val status:Int, val message:String, val data: T)

data class ResLoginData (var name : String , var token: String)
data class ReqLoginData (var id : String , var pw: String)
interface RetrofitNetwork {
    @POST("auth/login")
    fun postLogin(@Body reqLoginData: ReqLoginData) : Call<Response<ResLoginData>>
}

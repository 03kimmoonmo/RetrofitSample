package com.codesample.retrofitsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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

        button.setOnClickListener{
            val loginId = editId.text.toString()
            val loginPw = editPassword.text.toString()

            service.postLogin(ReqLoginData(loginId, loginPw))?.enqueue(object : Callback<Response<ResLoginData>> {
                override fun onFailure(call: Call<Response<ResLoginData>>, t: Throwable) {
                }

                override fun onResponse(call: Call<Response<ResLoginData>>, response: retrofit2.Response<Response<ResLoginData>>) {
                    Log.d("Response :: ", response?.body().toString())
                    Toast.makeText(applicationContext, "성공", Toast.LENGTH_SHORT).show()
                    var intent = Intent(applicationContext, NoticeActivity::class.java)
                    startActivity(intent)
                    Token.token = response.body()!!.data.token
                }
            })
        }

    }
}
data class Response<T>(
        val status:Int,
        val message:String,
        val data: T
        )

data class ResLoginData (
        var name : String ,
        var token: String
        )

data class ReqLoginData (
        var id : String ,
        var pw: String
        )
data class ResponseData (
        var idx : Int,
        var title : String,
        var content : String,
        var writer : String,
        var createdAt : String,
        var updatedAt : String
        )

interface RetrofitNetwork {
    @POST("auth/login")
    fun postLogin(@Body reqLoginData: ReqLoginData) : Call<Response<ResLoginData>>

    @GET("noticeBoard")
    fun getNotice(@Header("Authorization") token: String) : Call<Response<List<ResponseData>>>
}

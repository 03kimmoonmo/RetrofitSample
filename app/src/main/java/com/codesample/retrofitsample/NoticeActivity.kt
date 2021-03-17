 package com.codesample.retrofitsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_notice.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NoticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.80.163.40:4000/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        val service = retrofit.create(RetrofitNetwork::class.java)
        val token = Token.token
        Log.d("TAG", token)

        service.getNotice(token).enqueue(object : Callback<Response<List<ResponseData>>>{
            override fun onResponse(
                call: Call<Response<List<ResponseData>>>,
                response: retrofit2.Response<Response<List<ResponseData>>>
            ) {
                Log.d("Response : ", response.body()!!.data[0].title)
                val adapter     = KakaoAdapter(response.body()!!.data as MutableList<ResponseData>)
                val recyclerView : RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<Response<List<ResponseData>>>, t: Throwable) {
                Log.e("TAG", t.message.toString())
            }


        })
    }
}
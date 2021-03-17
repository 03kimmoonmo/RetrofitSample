package com.codesample.retrofitsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KakaoAdapter(val datas: MutableList<ResponseData>): RecyclerView.Adapter<ViewHoler>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kakao_item, parent, false)
        return ViewHoler(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        holder.titleTextView.text = datas[position].title
        holder.contentTextView.text = datas[position].content
        holder.writerTextView.text = datas[position].writer
    }

}

class ViewHoler(view: View): RecyclerView.ViewHolder(view){
    val titleTextView = view.findViewById<TextView>(R.id.title_textView)
    val contentTextView = view.findViewById<TextView>(R.id.content_textView)
    val writerTextView = view.findViewById<TextView>(R.id.writer_textView)
}
package com.plum.KGB.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.plum.KGB.myapplication.R
import com.plum.KGB.myapplication.ViewModel.CommentViewModel

class CommentAdapter(private val model: CommentViewModel): RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name : TextView = view.findViewById(R.id.item_comment_name)
        val date : TextView = view.findViewById(R.id.item_comment_date)
        val content : TextView = view.findViewById(R.id.item_comment_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = model.comments.value?.get(position)?.name
        holder.date.text = model.comments.value?.get(position)?.date
        holder.content.text = model.comments.value?.get(position)?.content
    }

    override fun getItemCount() = model.comments.value?.size ?: 0
}
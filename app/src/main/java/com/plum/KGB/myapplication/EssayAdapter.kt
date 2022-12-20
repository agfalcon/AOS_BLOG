package com.plum.KGB.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EssayAdapter(private val model: EssayViewModel): RecyclerView.Adapter<EssayAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), OnClickListener{
        var title: TextView = view.findViewById(R.id.edit_title)
        var content: TextView = view.findViewById(R.id.edit_content)
        var comment: TextView = view.findViewById(R.id.edit_comment)
        var date: TextView = view.findViewById(R.id.edit_date)
        override fun onClick(v: View?) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.essay_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = model.essays.value?.get(position)?.title
        holder.content.text = model.essays.value?.get(position)?.content
        holder.comment.text = model.essays.value?.get(position)?.commentNum.toString()
        holder.date.text = model.essays.value?.get(position)?.date
    }

    override fun getItemCount() = model.essays.value?.size ?: 0
}
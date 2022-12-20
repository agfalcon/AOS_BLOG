package com.plum.KGB.myapplication.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.plum.KGB.myapplication.View.EditShowActivity
import com.plum.KGB.myapplication.R
import com.plum.KGB.myapplication.ViewModel.EssayViewModel

class EssayAdapter(private val model: EssayViewModel, private val context: Context): RecyclerView.Adapter<EssayAdapter.ViewHolder>() {

    companion object{
        const val EDIT_ID = "edit"
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), OnClickListener{
        var title: TextView = view.findViewById(R.id.edit_title)
        var content: TextView = view.findViewById(R.id.edit_content)
        var comment: TextView = view.findViewById(R.id.edit_comment)
        var date: TextView = view.findViewById(R.id.edit_date)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val intent = Intent(context, EditShowActivity::class.java)
            intent.putExtra(EDIT_ID, model.essays.value?.get(adapterPosition))
            startActivity(context, intent, null)
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
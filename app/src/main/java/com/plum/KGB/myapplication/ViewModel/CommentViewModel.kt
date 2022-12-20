package com.plum.KGB.myapplication.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import org.json.JSONArray
import org.json.JSONObject

class CommentViewModel(application: Application) : AndroidViewModel (application) {
    data class Comment(var id: Int, var editId: Int, var content: String, var date: String)

    companion object{
        const val SERVER_URL = "https://sg-blog-db-lzrnx.run.goorm.io/comment"
        const val QUEUE_TAG = "CommentQueueTag"
    }

    private val list = ArrayList<Comment>()
    private val _comments = MutableLiveData<ArrayList<Comment>>()
    val comments : LiveData<ArrayList<Comment>>
        get() = _comments

    private var queue: RequestQueue

    init{
        _comments.value = list
        queue = newRequestQueue(getApplication())
    }

    fun requestComment(editId: Int){
        val request = JsonArrayRequest(
            Request.Method.GET,
            "$SERVER_URL/$editId",
            null,
            {
                //Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
                list.clear()
                parseJson(it)
                _comments.value = list
                Toast.makeText(getApplication(), list.toString(), Toast.LENGTH_LONG).show()

            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
            }
        )
        request.tag = QUEUE_TAG
        queue.add(request)
    }

    private fun parseJson(items: JSONArray){
        for(i in 0 until items.length()){
            val item = items[i] as JSONObject
            val id = item.getInt("id")
            val editId = item.getInt("edit_id")
            val date = item.getString("date")
            val content = item.getString("content")
            list.add(Comment(id, editId, content, date))
        }
    }

    override fun onCleared() {
        super.onCleared()
        queue.cancelAll(QUEUE_TAG)
    }
}
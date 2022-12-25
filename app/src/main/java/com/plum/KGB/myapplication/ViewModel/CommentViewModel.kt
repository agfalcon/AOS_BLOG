package com.plum.KGB.myapplication.ViewModel

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate

class CommentViewModel(application: Application) : AndroidViewModel (application) {
    data class Comment(var id: Int, var editId: Int, var name: String, var content: String, var date: String)

    companion object{
        const val GET_SERVER_URL = "https://sg-blog-db-lzrnx.run.goorm.io/comment"
        const val GET_QUEUE_TAG = "CommentQueueTag"
        const val POST_SERVER_URL = "https://sg-blog-db-lzrnx.run.goorm.io/editComment"
        const val POST_QUEUE_TAG = "SendCommentQueueTag"
    }

    private var editID: Int = 0

    private val list = ArrayList<Comment>()
    private val _comments = MutableLiveData<ArrayList<Comment>>()
    val comments : LiveData<ArrayList<Comment>>
        get() = _comments

    private var comment = Comment(0, editID, "","","")
    private val _commentEdit = MutableLiveData<Comment>()
    val commentEdit: LiveData<Comment>
        get() = _commentEdit

    private var queue: RequestQueue

    init{
        _comments.value = list
        _commentEdit.value = comment
        queue = newRequestQueue(getApplication())
    }

    private val commentData = JSONObject()

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendComment(name: String, con: String){
        commentData.put("editId", editID)
        commentData.put("name", name)
        commentData.put("content", con)
        commentData.put("date", LocalDate.now().toString())
        val request = JsonObjectRequest(
            Request.Method.POST,
            POST_SERVER_URL,
            commentData,
            {
                //Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
            }
        )
        request.tag = POST_QUEUE_TAG
        queue.add(request)
        comment = Comment(0, editID, "","","")
        _commentEdit.value = comment
    }

    fun requestComment(editId: Int){
        val request = JsonArrayRequest(
            Request.Method.GET,
            "$GET_SERVER_URL/$editId",
            null,
            {
                //Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
                list.clear()
                parseJson(it)
                _comments.value = list
                //Toast.makeText(getApplication(), list.toString(), Toast.LENGTH_LONG).show()

            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
            }
        )
        request.tag = GET_QUEUE_TAG
        queue.add(request)
    }

    private fun parseJson(items: JSONArray){
        for(i in 0 until items.length()){
            val item = items[i] as JSONObject
            val id = item.getInt("id")
            val editId = item.getInt("edit_id")
            val name = item.getString("name")
            val date = item.getString("date")
            val content = item.getString("content")
            list.add(Comment(id, editId, name,content, date))
        }
    }

    fun getEditID(id: Int){
        editID = id
    }

    override fun onCleared() {
        super.onCleared()
        queue.cancelAll(GET_QUEUE_TAG)
        queue.cancelAll(POST_QUEUE_TAG)
    }
}
package com.plum.KGB.myapplication.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

class EssayViewModel(application: Application) : AndroidViewModel(application) {
    data class Essay(var id: Int, var title: String, var content: String, var date: String, var commentNum: Int) : Serializable

    companion object{
        const val SERVER_URL = "https://sg-blog-db-lzrnx.run.goorm.io/essay"
        const val QUEUE_TAG = "EssayQueueTag"
    }

    private val list = ArrayList<Essay>()
    private val _essays = MutableLiveData<ArrayList<Essay>>()
    val essays : LiveData<ArrayList<Essay>>
        get() = _essays

    private val queue: RequestQueue

    init{
        _essays.value = list
        queue = Volley.newRequestQueue(getApplication())
    }

    fun requestEssay(){
        val request = JsonArrayRequest(
            Request.Method.GET,
            SERVER_URL,
            null,
            {
                //Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
                list.clear()
                parseJson(it)
                _essays.value = list
                //Toast.makeText(getApplication(), list.toString(), Toast.LENGTH_LONG).show()
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
            val title = item.getString("title")
            val date = item.getString("date").replace("T00:00:00.000Z", "")
            val content = item.getString("content")
            val commentNum = item.getInt("comment_num")
            list.add(Essay(id, title, content, date, commentNum))
        }
    }

    override fun onCleared() {
        super.onCleared()
        queue.cancelAll(QUEUE_TAG)
    }
}
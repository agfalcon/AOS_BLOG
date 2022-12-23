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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import org.json.JSONObject
import java.time.LocalDate

class EditViewModel(application: Application) : AndroidViewModel(application) {

    data class Content(var title: String, var content: String)

    companion object{
        const val SERVER_URL = "https://sg-blog-db-lzrnx.run.goorm.io/edit"
        const val QUEUE_TAG = "EditQueueTag"
    }

    private val _essays = MutableLiveData<Content>()
    private val editData = JSONObject()
    val essays : LiveData<Content>
        get() = _essays

    private val queue: RequestQueue

    init{
        queue = newRequestQueue(getApplication())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun requestEdit(){
        editData.put("title", essays.value?.title)
        editData.put("content", essays.value?.content)
        editData.put("date", LocalDate.now().toString())
        editData.put("comment_num", 0)
        val request = JsonObjectRequest(
            Request.Method.POST,
            SERVER_URL,
            editData,
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
            }
        )
        request.tag = QUEUE_TAG
        queue.add(request)
        _essays.value = null
    }

    override fun onCleared() {
        super.onCleared()
        queue.cancelAll(QUEUE_TAG)
    }
}
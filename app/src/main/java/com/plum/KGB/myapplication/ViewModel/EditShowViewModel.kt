package com.plum.KGB.myapplication.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley.newRequestQueue

class EditShowViewModel(application: Application) : AndroidViewModel(application) {

    companion object{
        const val DELETE_SERVER = "https://sg-blog-db-lzrnx.run.goorm.io/delete"
        const val DELETE_QUEUE = "DeleteQueueTag"
    }

    private val queue: RequestQueue

    init{
        queue = newRequestQueue(getApplication())
    }

    fun deleteRequest(id: Int){
        val request = JsonObjectRequest(
            Request.Method.GET,
            "$DELETE_SERVER/$id",
            null,
            {

            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_SHORT).show()
            }
        )
        request.tag = DELETE_QUEUE
        queue.add(request)
    }

    override fun onCleared() {
        super.onCleared()
        queue.cancelAll(DELETE_QUEUE)
    }
}
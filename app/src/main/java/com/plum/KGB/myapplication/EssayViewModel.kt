package com.plum.KGB.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class EssayViewModel(application: Application) : AndroidViewModel(application) {
    data class Essay(var id: Int, var title: String, var content: String, var date: String, var image: String)

    companion object{
        const val SERVER_URL = ""
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

    }
}
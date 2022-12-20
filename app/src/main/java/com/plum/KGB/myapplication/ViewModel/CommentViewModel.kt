package com.plum.KGB.myapplication.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CommentViewModel(application: Application) : AndroidViewModel (application) {
    data class Comment(var id: Int, var editId: Int, var content: String, var date: String)

    private val list = ArrayList<Comment>()
    private val _comments = MutableLiveData<ArrayList<Comment>>()
    val comments : LiveData<ArrayList<Comment>>
        get() = _comments
    
}
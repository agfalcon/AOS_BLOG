package com.plum.KGB.myapplication.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.plum.KGB.myapplication.databinding.ActivityCommentBinding

class CommentActivity : AppCompatActivity() {

    lateinit var binding: ActivityCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
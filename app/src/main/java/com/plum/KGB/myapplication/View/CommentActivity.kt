package com.plum.KGB.myapplication.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.plum.KGB.myapplication.Adapter.CommentAdapter
import com.plum.KGB.myapplication.Adapter.EssayAdapter
import com.plum.KGB.myapplication.ViewModel.CommentViewModel
import com.plum.KGB.myapplication.databinding.ActivityCommentBinding

class CommentActivity : AppCompatActivity() {

    lateinit var binding: ActivityCommentBinding
    lateinit var model: CommentViewModel
    lateinit var commentAdapter: CommentAdapter
    private var editID: Int = 0
    var commentNum: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[CommentViewModel::class.java]
        commentAdapter = CommentAdapter(model)

        binding.commentList.apply{
            layoutManager = LinearLayoutManager(application)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            adapter = commentAdapter
        }

        model.comments.observe(this){
            commentAdapter.notifyDataSetChanged()
        }

        commentNum = intent.getIntExtra(EditShowActivity.COMMENT_NUM, 0)
        editID = intent.getIntExtra(EssayAdapter.EDIT_ID, 0)
        model.requestComment(editID)
    }
}
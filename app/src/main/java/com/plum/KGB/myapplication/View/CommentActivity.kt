package com.plum.KGB.myapplication.View

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
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


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        commentNum = intent.getIntExtra(EditShowActivity.COMMENT_NUM, 0)
        editID = intent.getIntExtra(EssayAdapter.EDIT_ID, 0)

        model = ViewModelProvider(this)[CommentViewModel::class.java]
        model.getEditID(editID)
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


        model.requestComment(editID)

        binding.btnCommentSend.setOnClickListener {
            model.sendComment(binding.commentName.text.toString(), binding.commentEdit.text.toString())
            binding.commentName.setText("")
            binding.commentEdit.setText("")
            model.requestComment(editID)
            commentNum++
        }

        binding.commentBtnBack.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            //startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent()
        intent.putExtra("comment_num", commentNum)
        setResult(RESULT_OK, intent)
        finish()
    }
}
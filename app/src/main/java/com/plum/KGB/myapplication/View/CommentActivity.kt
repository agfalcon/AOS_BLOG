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
import com.plum.KGB.myapplication.ViewModel.EssayViewModel
import com.plum.KGB.myapplication.databinding.ActivityCommentBinding

class CommentActivity : AppCompatActivity() {

    lateinit var binding: ActivityCommentBinding
    lateinit var model: CommentViewModel
    lateinit var commentAdapter: CommentAdapter
    private var editID: Int = 0
    var commentNum: Int = 0
    lateinit var essay: EssayViewModel.Essay


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        essay = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EssayAdapter.EDIT_ID, EssayViewModel.Essay::class.java) ?: EssayViewModel.Essay(0, "", "", "", 0)
        } else{
            intent.getSerializableExtra(EssayAdapter.EDIT_ID) as EssayViewModel.Essay
        }

        commentNum = essay.commentNum
        editID = essay.id

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
            essay.commentNum++
        }

        binding.commentBtnBack.setOnClickListener {
            val intent = Intent(this, EditShowActivity::class.java)
            intent.putExtra(EssayAdapter.EDIT_ID, essay)
            startActivity(intent)
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
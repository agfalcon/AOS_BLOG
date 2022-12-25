package com.plum.KGB.myapplication.View

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.plum.KGB.myapplication.Adapter.EssayAdapter
import com.plum.KGB.myapplication.ViewModel.EditShowViewModel
import com.plum.KGB.myapplication.ViewModel.EssayViewModel
import com.plum.KGB.myapplication.databinding.ActivityEditShowBinding

class EditShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditShowBinding
    private lateinit var essay: EssayViewModel.Essay
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var model : EditShowViewModel

    companion object{
        const val COMMENT_NUM = "commentNum"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[EditShowViewModel::class.java]

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode != RESULT_OK)
                return@registerForActivityResult

            val num = it.data?.getIntExtra("comment_num", essay.commentNum)
            binding.editShowComment.text = num.toString()
        }

        initEdit()
    }

    private fun initEdit(){

        essay = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(EssayAdapter.EDIT_ID, EssayViewModel.Essay::class.java) ?: EssayViewModel.Essay(0, "", "", "", 0)
        } else{
            intent.getSerializableExtra(EssayAdapter.EDIT_ID) as EssayViewModel.Essay
        }

        binding.editShowTitle.text = essay.title
        binding.editShowDate.text = essay.date
        binding.editShowContent.text = essay.content
        binding.editShowComment.text = essay.commentNum.toString()

        binding.btnComment.setOnClickListener {
            val intent = Intent(this, CommentActivity::class.java)
            intent.putExtra(EssayAdapter.EDIT_ID, essay)
            //intent.putExtra(EssayAdapter.EDIT_ID, essay.id)
            //intent.putExtra(COMMENT_NUM, essay.commentNum)
            launcher.launch(intent)
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnDelete.setOnClickListener {
            model.deleteRequest(essay.id)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
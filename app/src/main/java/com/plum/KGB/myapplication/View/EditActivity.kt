package com.plum.KGB.myapplication.View

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.plum.KGB.myapplication.ViewModel.EditViewModel
import com.plum.KGB.myapplication.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private lateinit var model: EditViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[EditViewModel::class.java]

        binding.editTitle.doAfterTextChanged {
            model.getTitle(binding.editTitle.text.toString())
        }

        binding.editContent.doAfterTextChanged {
            model.getContent(binding.editContent.text.toString())
        }

        binding.btnEditExit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnEditSend.setOnClickListener {
            model.requestEdit()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
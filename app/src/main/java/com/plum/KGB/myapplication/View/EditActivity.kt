package com.plum.KGB.myapplication.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.plum.KGB.myapplication.ViewModel.EditViewModel
import com.plum.KGB.myapplication.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private lateinit var model: EditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[EditViewModel::class.java]

        binding.editTitle.doAfterTextChanged {
            model.essays.value?.title = binding.editTitle.text.toString()
        }

        binding.editContent.doAfterTextChanged {
            model.essays.value?.content = binding.editContent.text.toString()
        }

        binding.btnEditSend.setOnClickListener {
            model.requestEdit()
        }
    }
}
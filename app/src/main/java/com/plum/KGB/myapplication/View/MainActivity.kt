package com.plum.KGB.myapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.plum.KGB.myapplication.Adapter.EssayAdapter
import com.plum.KGB.myapplication.ViewModel.EssayViewModel
import com.plum.KGB.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var essayViewModel: EssayViewModel
    private lateinit var essayAdapter : EssayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        essayViewModel = ViewModelProvider(this)[EssayViewModel::class.java]
        essayAdapter = EssayAdapter(essayViewModel, this)

        essayViewModel.essays.observe(this){
            essayAdapter.notifyDataSetChanged()
        }

        binding.listItem.apply{
            layoutManager = LinearLayoutManager(application)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            adapter = essayAdapter
        }
        essayViewModel.requestEssay()

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
    }
}
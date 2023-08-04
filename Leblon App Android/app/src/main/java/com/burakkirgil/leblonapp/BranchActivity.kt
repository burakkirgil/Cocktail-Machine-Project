package com.burakkirgil.leblonapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.burakkirgil.leblonapp.databinding.ActivityBranchBinding
import com.burakkirgil.leblonapp.databinding.ActivityMainBinding
import com.burakkirgil.leblonapp.databinding.ActivitySeatBinding

private lateinit var binding : ActivityBranchBinding
class BranchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branch)
        binding = ActivityBranchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
    fun chooseHisar(view : View){
        val intent = Intent(this@BranchActivity, SeatActivity::class.java)
        intent.putExtra("branch","hisar kampüs")
        startActivity(intent)
        finish()
    }
    fun chooseGuney(view : View){
        val intent = Intent(this@BranchActivity, SeatActivity::class.java)
        intent.putExtra("branch","güney kampüs")
        startActivity(intent)
        finish()
    }
    fun chooseKuzey(view : View){
        val intent = Intent(this@BranchActivity, SeatActivity::class.java)
        intent.putExtra("branch","kuzey kampüs")
        startActivity(intent)
        finish()
    }
}
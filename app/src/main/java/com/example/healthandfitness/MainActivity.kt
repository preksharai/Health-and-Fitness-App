package com.example.healthandfitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ll_btn_start.setOnClickListener {
            val intent= Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }
        ll_btn_history.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        ll_btn_bmi.setOnClickListener{
            val intent = Intent(this, calc_BMI::class.java)
            startActivity(intent)

        }
    }
}
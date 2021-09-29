package com.example.healthandfitness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calc__b_m_i.*
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_history.*

class calc_BMI : AppCompatActivity() {
    var ans:Double=0.0
    var val1:Double=0.0
    var val2:Double=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc__b_m_i)

        setSupportActionBar(toolbar_bmi)
        var toolbar3=supportActionBar
        if(toolbar3!=null)
            toolbar3.setDisplayHomeAsUpEnabled(true)
        toolbar_bmi.setNavigationOnClickListener {
            onBackPressed()
        }

        btn_get_bmi.setOnClickListener {
            if (et_weight.text.toString().isEmpty() || et_height.text.toString().isEmpty())
                Toast.makeText(this, "Field can't be empty.", Toast.LENGTH_SHORT).show()
            else
            {
                val1=(et_weight.text.toString()).toDouble()
                val2=((et_height.text.toString()).toDouble())*((et_height.text.toString()).toDouble())
                ans=val1/val2
                show_bmi.text="BMI is : ${ans.toString()}"
                if(ans>=25.0)
                    Toast.makeText(this, "You are overweight!", Toast.LENGTH_SHORT).show()
                else if(ans<=18.5)
                    Toast.makeText(this, "You are underweight!", Toast.LENGTH_SHORT).show()
                else if(ans>18.5 && ans<=24.9)
                    Toast.makeText(this, "You have normal weight!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
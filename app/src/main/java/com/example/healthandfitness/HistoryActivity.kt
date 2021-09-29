package com.example.healthandfitness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    var historyAdapter:HistoryAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(historyActivityToolbar)
        var toolbar3=supportActionBar
        if(toolbar3!=null)
            toolbar3.setDisplayHomeAsUpEnabled(true)
        historyActivityToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        getAllDates()
    }

    //to show date list
    fun getAllDates()
    {
        val dbHandler=SqliteOpenHelper(this,null)
        //getting arrayLIst from db
        val datesList=dbHandler.getDates()

        Log.e("date", datesList.size.toString())

        if(datesList.size>0)
        {
            history_RV.visibility= View.VISIBLE
            tvhistory.visibility=View.GONE
            no_data_available.visibility=View.GONE

            history_RV.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            historyAdapter=HistoryAdapter(this,datesList)
            history_RV.adapter=historyAdapter
        }
        else
        {
            history_RV.visibility= View.GONE
            tvhistory.visibility=View.VISIBLE
            no_data_available.visibility=View.VISIBLE
        }
    }



}
package com.example.healthandfitness

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_finish_activity.*
import java.text.SimpleDateFormat
import java.util.*

class finish_activity : AppCompatActivity() {

    var player:MediaPlayer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_activity)


        player=MediaPlayer.create(applicationContext,R.raw.applause3)
        player!!.isLooping=false
        player!!.start()


        finish_btn.setOnClickListener{
            val intent=Intent(this@finish_activity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        calculateDate()
    }

    fun calculateDate()
    {
        val c=Calendar.getInstance()

        //c.time gives both date and time in a single string
        val dateTime=c.time
        //it is necessary to give sdf so that hte dateTime we got is in millisec, sdf will convert into desired format
        val sdf=SimpleDateFormat("dd MM yyyy HH:mm:ss",Locale.getDefault())
        val date=sdf.format(dateTime)

        //creating object of the sqlite class created in SqliteOpenHelper so that class ka method can be called
        val dbHandler=SqliteOpenHelper(this,null)
        dbHandler.addDate(date)
    }

    override fun onDestroy() {
        if(player!=null)
        {
            player!!.stop()
        }
        super.onDestroy()
    }

}
package com.example.healthandfitness

import android.content.Intent
import android.media.MediaDrm
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

    var exerciselist:ArrayList<ExerciseModel>?=null
    var currentExercisePosition=-1

    var adapterObj:RV_adapter?=null

    //creating object
    var tts:TextToSpeech?=null
    var player:MediaPlayer?=null

    private var restprogress=0
    //resttimer is an object of class CountDownTImer, just object declaration
    private var resttimer:CountDownTimer?=null
    private var exerciseprogress=0
    private var exercisetimer:CountDownTimer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        //initialising object, para1:this, para2:which method uses it?=>this:goto class then interface then init function
        tts=TextToSpeech(this,this)

        //action bar wala kaam toolbar krega
        setSupportActionBar(toolbar)

        //crreating object of toolbar
        val actionbar=supportActionBar
        if(actionbar!=null)
            //it specifies that on pressing back button it goes one activity back only
            actionbar.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        exerciselist=ExerciseController.ExerciseFunction()
        setupRecyclerView()
        setupRestView()

    }

    override fun onDestroy() {

        //to reset the timer if user presses back button
        if(resttimer!=null) {
            resttimer!!.cancel()   //to cancel the timer if the timer is finished
            restprogress=0
        }
        if(exercisetimer!=null) {
            exercisetimer!!.cancel()   //to cancel the timer if the timer is finished
            exerciseprogress=0
        }
        if(tts!=null)
        {
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player!=null)
        {
            player!!.stop()
        }
        super.onDestroy()
    }

    fun setupRestProgressbar()
    {
        progress_bar.progress=restprogress
        //creating an object...this class takes 2 args(max time in millisec,gaping in millisec)
        //error bcz CountDownTimer is an abstract class, so it's object can't be created
        //implements it's two functions
        //object: this prefix is needed
        resttimer=object:CountDownTimer(10000,1000){
            override fun onTick(milliUntilFinished:Long)
            {
                //this method will be called by itself bcz of "on"prefix, we don't need to call
                //it will be called after every 1 sec
                //if you wanna do any manipulation for every sec, then can do here
                progress_bar.progress=10-restprogress
                timer.text=(10-restprogress).toString()
                restprogress+=1

            }
            override fun onFinish()
            {
                //this method will be called by itself bcz of "on"prefix, we don't need to call
                //it will be called when time is over.
                //Toast.makeText(this@ExerciseActivity,"Now,exercise will start",Toast.LENGTH_LONG).show()
                currentExercisePosition++

                //to change color of the item of RVA
                exerciselist!![currentExercisePosition].isSelected=true
                //adapter will get to know that something is going to be changed
                //so onBindViewHolder of recycler view will be called then
                adapterObj!!.notifyDataSetChanged()

                setupExerciseView()

            }
        }.start()

    }

    fun setupExerciseProgressbar()
    {
        exercise_progress_bar.progress=exerciseprogress
        exercisetimer=object:CountDownTimer(30000,1000){
            override fun onTick(milliUntilFinished:Long)
            {
                exercise_progress_bar.progress=30-exerciseprogress
                exercise_timer.text=(30-exerciseprogress).toString()
                exerciseprogress+=1

            }
            override fun onFinish()
            {
                if(currentExercisePosition<(exerciselist?.size!!-1)) {

                    //to notify the adapter that something is going to be changed.
                    exerciselist!![currentExercisePosition].isSelected=false
                    exerciselist!![currentExercisePosition].isCompleted=true
                    adapterObj!!.notifyDataSetChanged()

                    setupRestView()
                }
                else {
                    val intent = Intent(this@ExerciseActivity, finish_activity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }.start()

    }

    //to reset timer
    fun setupRestView()
    {
        //initialising object, para1:applicationContext, para2:file
        player=MediaPlayer.create(applicationContext,R.raw.thud)
        player!!.isLooping=false
        player!!.start()

        rest_linear_layout.visibility= View.VISIBLE
        exercise_linear_layout.visibility=View.GONE
        //checking agr time chl gya h
        if(resttimer!=null) {
            resttimer!!.cancel()   //to cancel the timer if the timer is finished
            restprogress=0
        }


        exerciselist=ExerciseController.ExerciseFunction()
        upcomingexercisename.text=exerciselist!![currentExercisePosition+1].name
        setupRestProgressbar()

    }
    fun setupExerciseView()
    {
        rest_linear_layout.visibility= View.GONE
        exercise_linear_layout.visibility=View.VISIBLE
        //checking agr time chl gya h
        if(exercisetimer!=null) {
            exercisetimer!!.cancel()   //to cancel the timer if the timer is finished
            exerciseprogress=0
        }
        saytheword(exerciselist!![currentExercisePosition].name)
        setupExerciseProgressbar()
        exercise_image.setImageResource(exerciselist!!.get(currentExercisePosition).image)
        exercise_name.text=exerciselist!![currentExercisePosition].name
    }

    //status checks if this feature is present in user's phone or not
    override fun onInit(status: Int) {
       if(status==TextToSpeech.SUCCESS)
       {
           val result=tts!!.setLanguage(Locale.US)
           //ya bol na paya, ya word supported ni h
           if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED)
               Log.e("error","lang not supported")

       }
        else
           //this msg is for us only, not for user
           Log.e("error","text to speech not found")

    }
    fun saytheword(text:String)
    {
        //para1:text to be spoken, para2:if new sentence is given then that should be spoken(flush), or continue prev one(add)
        //para3:any data from other activity
        //para4:to set dilect or accent:string value
        tts!!.speak(text,TextToSpeech.QUEUE_ADD,null,"")
    }

    fun setupRecyclerView()
    {
        //this manager gives the layout of recycler view,ie, horizontal and no reverse display
        appRecyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        adapterObj= RV_adapter(exerciselist!!,this)
        appRecyclerView.adapter=adapterObj
    }

}

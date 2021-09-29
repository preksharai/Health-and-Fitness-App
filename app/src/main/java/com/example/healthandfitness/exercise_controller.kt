package com.example.healthandfitness

object ExerciseController
{
    fun ExerciseFunction():ArrayList<ExerciseModel>
    {
        var exerciseList=ArrayList<ExerciseModel>()
        var ex1=ExerciseModel(1,"Sit-up",R.drawable.sit_up,false,false)
        var ex2=ExerciseModel(2,"pushups",R.drawable.push_up_,false,false)
        var ex3=ExerciseModel(3,"pull-up",R.drawable.pull_up,false,false)
        exerciseList.add(ex1)
        exerciseList.add(ex2)
        exerciseList.add(ex3)
        return exerciseList
    }
}
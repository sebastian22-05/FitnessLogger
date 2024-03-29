package com.example.fitnesslogger

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import java.util.ArrayList

//code inspired by https://www.geeksforgeeks.org/how-to-read-data-from-sqlite-database-in-android/
class ExerciseAdapter     // constructor
    (// variable for our array list and context
    private val exerciseModelArrayList: ArrayList<ExerciseModel>, private val context: Context
) : RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // on below line we are inflating our layout
        // file for our recycler view items.
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_display_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // on below line we are setting data
        // to our views of recycler view item.
        val (exercise, date) = exerciseModelArrayList[position]
        holder.exerciseName.text = exercise
        holder.exerciseDate.text = date


        holder.itemView.setOnClickListener { // on below line we are calling an intent.
            val i = Intent(context, UpdateExerciseActivity::class.java)
            //gets the exercise that was clicked
            i.putExtra("name", exercise)
            i.putExtra("date", date)
            // starting our activity.
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        // returning the size of our array list
        return exerciseModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // creating variables for our text views.
        val exerciseName: TextView = itemView.findViewById(R.id.exerciseName)
        val exerciseDate: TextView = itemView.findViewById(R.id.exerciseDate)

    }
}
package com.example.fitnesslogger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import kotlinx.android.synthetic.main.activity_update_exercise.*
//code inspired by https://www.geeksforgeeks.org/how-to-update-data-to-sqlite-database-in-android/
class UpdateExerciseActivity : AppCompatActivity() {
    private var db: DBHelper? = null
    var exercise: String = ""
    var date: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_exercise)

        db = DBHelper(this@UpdateExerciseActivity, null)
        exercise = intent.getStringExtra("name").toString()
        date = intent.getStringExtra("date").toString()

        edtExercise.setText(exercise)
        txtDate.text = date
        //updates the exercise and redirects user to the home page
        btnUpdateExercise.setOnClickListener{
            db?.updateExercise(exercise, edtExercise.text.toString(), txtDate.text.toString())
            Toast.makeText(this@UpdateExerciseActivity, "Exercise Updated", Toast.LENGTH_SHORT).show()
            val i = Intent(this@UpdateExerciseActivity, MainActivity::class.java)
            startActivity(i)
        }
        //deletes the exercise and redirects user to the home page
        btnDeleteExercise.setOnClickListener {
            db?.deleteExercise(exercise, txtDate.text.toString())
            Toast.makeText(this@UpdateExerciseActivity, "Exercise Deleted", Toast.LENGTH_SHORT).show()
            val i = Intent(this@UpdateExerciseActivity, MainActivity::class.java)
            startActivity(i)
        }

    }

}
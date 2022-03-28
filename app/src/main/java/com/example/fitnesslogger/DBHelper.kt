package com.example.fitnesslogger

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.fitnesslogger.databinding.FragmentFirstBinding
import java.util.ArrayList

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                EXERCISE_COL + " TEXT," +
                DATE_COL + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addExercise(exercise : String, date : String ){

        val values = ContentValues()

        values.put(EXERCISE_COL, exercise)
        values.put(DATE_COL, date)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    fun readExercises(): ArrayList<ExerciseModel> {
        val db = this.readableDatabase
        val exerciseModelArrayList: ArrayList<ExerciseModel> = ArrayList<ExerciseModel>()
        val cursorExercises = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

        if (cursorExercises.moveToFirst()) {
            do {
                exerciseModelArrayList.add(
                    ExerciseModel(
                        cursorExercises.getString(1),
                        cursorExercises.getString(2)
                    )
                )
            } while (cursorExercises.moveToNext())
        }
        cursorExercises.close()
        return exerciseModelArrayList
    }

    fun updateExercise(originalExercise : String, exercise: String){
        val values = ContentValues()
        val db = this.writableDatabase

        values.put(EXERCISE_COL, exercise)

        db.update(TABLE_NAME, values, "exercise=?", arrayOf(originalExercise))
        db.close()
    }

    fun deleteExercise(exercise: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "exercise=?", arrayOf(exercise))
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "WorkoutDB"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "Exercises"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for name column
        val EXERCISE_COL = "exercise"

        // below is the variable for age column
        val DATE_COL = "date"
    }
}

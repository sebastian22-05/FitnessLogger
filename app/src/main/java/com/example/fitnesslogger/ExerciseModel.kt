package com.example.fitnesslogger
//data class which makes getters and setters in kotlin for the parameters
//code inspired by https://www.geeksforgeeks.org/how-to-read-data-from-sqlite-database-in-android/
data class ExerciseModel (var exercise: String, var date: String) {
    var id = 0
}
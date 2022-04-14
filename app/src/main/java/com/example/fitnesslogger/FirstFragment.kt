package com.example.fitnesslogger

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fitnesslogger.databinding.FragmentFirstBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val RQ_SPEECH_REC = 100
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.btnSpeak.setOnClickListener {
            speak()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //Function which allows the user to use their voice as an input
    //code inspired by https://www.youtube.com/watch?v=a7-Z9awsods
    private fun speak() {
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi, tell me your exercise")
        try {
            startActivityForResult(mIntent, RQ_SPEECH_REC)
        }

        catch (e: Exception) {

        }
    }

    //Function which recognises the speech input and then stores this to the database
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            RQ_SPEECH_REC -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    //instance of the database helper class is made
                    val db = context?.let { it1 -> DBHelper(it1, null) }
                    val exercise = result?.get(0)
                    val current = LocalDateTime.now()
                    //gets the device's date/time
                    val formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy")
                    val formatted = current.format(formatter)
                    val date = formatted.toString()
                    //calls the add function to save exercise to the databse
                    if (exercise != null) {
                        db?.addExercise(exercise, date)
                    }
                    //pop up to show which exercise has been added
                    Toast.makeText(activity, exercise + " added", Toast.LENGTH_LONG).show()

                    val i = Intent(activity, MainActivity::class.java)
                }
            }
        }
    }
}
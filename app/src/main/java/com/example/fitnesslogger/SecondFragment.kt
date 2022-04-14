package com.example.fitnesslogger

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_second.view.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesslogger.databinding.FragmentSecondBinding
import kotlinx.android.synthetic.*
import java.util.ArrayList

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
//code inspired by https://www.geeksforgeeks.org/how-to-read-data-from-sqlite-database-in-android/
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //initialises instances of other classes
        super.onViewCreated(view, savedInstanceState)
        var exerciseModelArrayList: ArrayList<ExerciseModel>? = null
        var exerciseAdapter: ExerciseAdapter? = null
        var exercisesRV: RecyclerView? = null
        //reads all values in the database
        val db = context?.let { it1 -> DBHelper(it1, null) }

        exerciseModelArrayList = db!!.readExercises()
        exerciseAdapter = activity?.let { ExerciseAdapter(exerciseModelArrayList, it) }
        exercisesRV = view?.findViewById(R.id.exerciseList)
        //shows all the exercises in the list format
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        exercisesRV!!.layoutManager = linearLayoutManager

        exercisesRV!!.adapter = exerciseAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
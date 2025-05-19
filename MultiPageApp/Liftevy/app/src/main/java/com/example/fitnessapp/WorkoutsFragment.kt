package com.example.fitpal

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R
import com.example.fitnessapp.WorkoutDetailActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat

class WorkoutsFragment : Fragment() {

    // Recycler view and adapter to display workout list
    private lateinit var recyclerView: RecyclerView
    private lateinit var workoutAdapter: WorkoutAdapter

    // Button to add a new workout
    private lateinit var addButton: FloatingActionButton

    // List of workouts
    private val workoutList = mutableListOf(
        Workout("Push Ups", "10 mins", "Medium"),
        Workout("Running", "30 mins", "Hard"),
        Workout("Yoga", "20 mins", "Easy"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_workouts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.workoutRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        workoutAdapter = WorkoutAdapter(workoutList) { workout ->
            // Navigate to WorkoutDetailActivity when a workout is clicked
            val intent = Intent(activity, WorkoutDetailActivity::class.java).apply {
                putExtra("workoutName", workout.name)
                putExtra("workoutDuration", workout.duration)
                putExtra("workoutDifficulty", workout.difficulty)
            }
            startActivity(intent)
        }

        recyclerView.adapter = workoutAdapter

        addButton = view.findViewById(R.id.btnAddWorkout)
        addButton.setOnClickListener {
            // Show the add workout dialog when the button is clicked
            showAddWorkoutDialog()
        }
    }

    // Show the add workout dialog
    private fun showAddWorkoutDialog() {
        val context = requireContext()
        val inflater = LayoutInflater.from(context)

        // Create the dialog view
        val dialogView = inflater.inflate(R.layout.dialog_add_workout, null).apply {
            setBackgroundColor(Color.parseColor("#191616")) // Ensure dark background
        }

        // Get the input fields
        val inputName = dialogView.findViewById<EditText>(R.id.inputWorkoutName)
        val inputDuration = dialogView.findViewById<EditText>(R.id.inputWorkoutDuration)
        val inputDifficulty = dialogView.findViewById<EditText>(R.id.inputWorkoutDifficulty)

        // Set the font for the input fields
        val poppinsFont = ResourcesCompat.getFont(context, R.font.poppins)

        listOf(inputName, inputDuration, inputDifficulty).forEach { editText ->
            editText.setTextColor(Color.WHITE)
            editText.setHintTextColor(Color.LTGRAY)
            editText.typeface = poppinsFont
        }

        // Set the title
        val titleView = TextView(context).apply {
            text = "Add Workout"
            textSize = 20f
            setTextColor(Color.WHITE)
            typeface = poppinsFont
            setPadding(48, 48, 48, 16)
        }

        // Create the dialog
        val dialog = AlertDialog.Builder(
            context,
            R.style.CustomAlertDialog
        ) // Style ensures no default white box
            .setCustomTitle(titleView)
            .setView(dialogView)
            .setPositiveButton("Add", null)  // Will override below
            .setNegativeButton("Cancel", null)
            .create()

        // Set the positive button's click listener
        dialog.setOnShowListener {
            val positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val negativeBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

            positiveBtn.setTextColor(Color.WHITE)
            negativeBtn.setTextColor(Color.LTGRAY)
            positiveBtn.typeface = poppinsFont
            negativeBtn.typeface = poppinsFont

            positiveBtn.setOnClickListener {
                // Add the workout to the list when the button is clicked
                val name = inputName.text.toString().trim()
                val duration = inputDuration.text.toString().trim()
                val difficulty = inputDifficulty.text.toString().trim()

                if (name.isNotEmpty() && duration.isNotEmpty() && difficulty.isNotEmpty()) {
                    val newWorkout = Workout(name, duration, difficulty)
                    workoutList.add(newWorkout)
                    workoutAdapter.notifyItemInserted(workoutList.size - 1)
                    dialog.dismiss()
                } else {
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
        // Show the dialog
        dialog.show()
    }
}


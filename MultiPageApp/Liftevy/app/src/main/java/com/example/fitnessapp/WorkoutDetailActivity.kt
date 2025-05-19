package com.example.fitnessapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class WorkoutDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_detail)

        // Retrieve workout details from the intent
        val workoutName = intent.getStringExtra(EXTRA_WORKOUT_NAME) ?: "Unknown Workout"
        val workoutDuration = intent.getStringExtra(EXTRA_WORKOUT_DURATION) ?: "Unknown Duration"
        val workoutDifficulty = intent.getStringExtra(EXTRA_WORKOUT_DIFFICULTY) ?: "Unknown Difficulty"

        // Set up the toolbar with the workout name
        setupToolbar(workoutName)
        // Display the workout details
        setupWorkoutDetails(workoutName, workoutDuration, workoutDifficulty)
    }

    private fun setupToolbar(workoutName: String) {
        supportActionBar?.apply {
            // Set the title of the toolbar
            title = workoutName
            setDisplayHomeAsUpEnabled(true)
        }

        // Configure the back button
        findViewById<ImageView?>(R.id.backButton)?.apply {
            setImageDrawable(ContextCompat.getDrawable(this@WorkoutDetailActivity, R.drawable.previous))
            setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }

    private fun setupWorkoutDetails(workoutName: String, workoutDuration: String, workoutDifficulty: String) {
        // Set the workout name on the detail view
        findViewById<TextView?>(R.id.detailWorkoutName)?.text = workoutName
        // Set the workout duration on the detail view
        findViewById<TextView?>(R.id.detailWorkoutDuration)?.text = "Duration: $workoutDuration"
        // Set the workout difficulty on the detail view
        findViewById<TextView?>(R.id.detailWorkoutDifficulty)?.text = "Difficulty: $workoutDifficulty"
    }

    override fun onSupportNavigateUp(): Boolean {
        // Handle the navigation up action
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        // Constants for intent extras
        const val EXTRA_WORKOUT_NAME = "workoutName"
        const val EXTRA_WORKOUT_DURATION = "workoutDuration"
        const val EXTRA_WORKOUT_DIFFICULTY = "workoutDifficulty"
    }
}


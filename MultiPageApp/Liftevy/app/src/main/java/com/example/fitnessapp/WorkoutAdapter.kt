package com.example.fitpal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnessapp.R

// Adapter class for the RecyclerView in the WorkoutsFragment
class WorkoutAdapter(
    private val workouts: List<Workout>,
    private val onItemClick: (Workout) -> Unit
) : RecyclerView.Adapter<WorkoutAdapter.ViewHolder>() {

    // ViewHolder class for the RecyclerView items
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // TextView for the workout name
        private val workoutNameTextView: TextView = itemView.findViewById(R.id.workout_name)

        // Bind the workout data to the ViewHolder
        fun bind(workout: Workout) {
            workoutNameTextView.text = workout.name

            // Set the click listener for the item
            itemView.setOnClickListener {
                onItemClick(workout)
            }
        }
    }

    // Create a new ViewHolder from the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout, parent, false)
        return ViewHolder(view)
    }

    // Bind the workout data to the ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workout = workouts[position]
        holder.bind(workout)
    }

    // Get the number of items in the RecyclerView
    override fun getItemCount() = workouts.size
}


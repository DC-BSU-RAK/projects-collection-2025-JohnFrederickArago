package com.example.fitpal

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fitnessapp.R

class HomeFragment : Fragment() {

    private lateinit var btnBeginner: Button
    private lateinit var btnIntermediate: Button
    private lateinit var btnProfessional: Button
    private lateinit var searchView: SearchView

    private lateinit var diffImage: ImageView

    private lateinit var sharedPreferences: SharedPreferences

    // Map of difficulty levels to their corresponding images
    private val difficultyImages = mapOf(
        "Beginner" to R.drawable.bgnr_workout,
        "Intermediate" to R.drawable.mid_workout,
        "Professional" to R.drawable.hard_workout
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI components
        btnBeginner = view.findViewById(R.id.btnBeginner)
        btnIntermediate = view.findViewById(R.id.btnIntermediate)
        btnProfessional = view.findViewById(R.id.btnProfessional)
        searchView = view.findViewById(R.id.home_search_view)
        diffImage = view.findViewById(R.id.difficulty_image)

        // Access shared preferences
        sharedPreferences = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)

        val buttons = listOf(btnBeginner, btnIntermediate, btnProfessional)

        // Check for saved difficulty preference
        val savedDifficulty = sharedPreferences.getString("selected_difficulty", null)
        savedDifficulty?.let { level ->
            updateDifficultyImage(level)
            highlightSelectedButton(level)
        }

        // Set click listeners for difficulty buttons
        buttons.forEach { button ->
            button.setOnClickListener {
                // Reset button styles
                buttons.forEach {
                    it.setBackgroundColor(Color.WHITE)
                    it.setTextColor(Color.parseColor("#191616"))
                }
                // Highlight selected button
                button.setBackgroundColor(Color.parseColor("#C1FF72"))
                button.setTextColor(Color.parseColor("#191616"))

                // Determine selected difficulty level
                val level = when (button.id) {
                    R.id.btnBeginner -> "Beginner"
                    R.id.btnIntermediate -> "Intermediate"
                    R.id.btnProfessional -> "Professional"
                    else -> "Unknown"
                }

                // Save selected difficulty to shared preferences
                sharedPreferences.edit().putString("selected_difficulty", level).apply()
                
                // Update the difficulty image
                updateDifficultyImage(level)

                // Show a toast message
                Toast.makeText(requireContext(), "$level level selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Update the difficulty image based on the selected level
    private fun updateDifficultyImage(level: String) {
        val imageRes = difficultyImages[level]
        if (imageRes != null) {
            diffImage.setImageResource(imageRes)
            diffImage.visibility = View.VISIBLE
        } else {
            diffImage.visibility = View.GONE
        }
    }

    // Highlight the button corresponding to the saved difficulty level
    private fun highlightSelectedButton(level: String) {
        when (level) {
            "Beginner" -> btnBeginner.performClick()
            "Intermediate" -> btnIntermediate.performClick()
            "Professional" -> btnProfessional.performClick()
        }
    }
}


package com.example.chordapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

// This is the main activity of the app. It sets up the UI, handles user input and shows the chord notes
class MainActivity : AppCompatActivity() {

    // The list of notes in the chromatic scale
    private val notes = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")


    // The list of supported chord types
    private val chordTypes = listOf("Major", "Minor", "Diminished")

    // Handles the logic for the spinner and the two modals
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rootNoteSpinner = findViewById<Spinner>(R.id.spinner_1)
        val chordTypeSpinner = findViewById<Spinner>(R.id.spinner_2)
        val calculateButton = findViewById<Button>(R.id.chordify)
        val infoButton = findViewById<ImageButton?>(R.id.info_button)

        // Set up the spinners with the notes and chord types
        setupSpinners(rootNoteSpinner, chordTypeSpinner)

        // Handle the calculate button click
        calculateButton.setOnClickListener {
            val root = rootNoteSpinner.selectedItem.toString()
            val type = chordTypeSpinner.selectedItem.toString()
            val chord = calculateChord(root, type)
            showChordDialog(root, type, chord)
        }

        // Handle the info button click
        infoButton?.setOnClickListener {
            showInfoDialog()
        }
    }

    // Set up the spinners with the notes and chord types
    private fun setupSpinners(rootNoteSpinner: Spinner, chordTypeSpinner: Spinner) {
        rootNoteSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, notes)
        chordTypeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, chordTypes)
    }

    // Calculate the chord notes for a given root note and chord type
    private fun calculateChord(root: String, type: String): List<String> {
        // Find the index of the root note in the chromatic scale
        val rootIndex = notes.indexOf(root)
        // Calculate the intervals for the given chord type
        val intervals = when (type) {
            "Major" -> listOf(0, 4, 7)
            "Minor" -> listOf(0, 3, 7)
            "Diminished" -> listOf(0, 3, 6)
            else -> listOf(0)
        }

        // Calculate the chord notes by adding the intervals to the root index
        return intervals.map { interval ->
            notes[(rootIndex + interval) % notes.size]
        }
    }

    // Show the chord notes in a modal
    private fun showChordDialog(root: String, type: String, chordNotes: List<String>) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.chordify, null)
        val chordModalText = dialogView.findViewById<TextView>(R.id.chord_modal)
        val rootLayout = dialogView.findViewById<View>(R.id.root_layout)

        // Adds a spinning animation to the modal
        val spinAnim = AnimationUtils.loadAnimation(this, R.anim.spin_in)
        rootLayout?.startAnimation(spinAnim)

        // Set the text of the modal to the chord notes
        val message = "$root $type chord:\n${chordNotes.joinToString(", ")}"
        chordModalText.text = message

        // Shows the dialog
        AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()
            .show()
    }

    // Shows the app instructions in a modal
    private fun showInfoDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.instructions, null)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()
            .show()
    }
}


package com.example.droidrenderdemoearth

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener

class GraphicsActivity : Activity() {

    private lateinit var gLView: GLSurfaceView

    companion object {

        var isStereoscopicEnabled = false
        var isBloomEnabled = true
        var bloomPasses = 6
        var stereoSpreadBase = 3.0f
        var stereoSpreadMax = 9.0f

        fun isTablet(context: Context?): Boolean {
            context?.let { _context ->
                val sizeData =
                    (_context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK)
                if (sizeData >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
                    return true
                }
            }
            return false
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a LinearLayout to contain the SeekBars, RadioGroup, and GLSurfaceView
        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(android.graphics.Color.TRANSPARENT)
        }

        // Create the SeekBar for the top slider (sliderA)
        val sliderA = SeekBar(this).apply {
            max = 5
            id = R.id.seekA
            setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    Log.d("GraphicsActivity", "Slider A value: $progress")
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // Do something when tracking starts
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // Do something when tracking stops
                }
            })
        }

        // Create the RadioGroup for the segmented picker
        val segmentedPicker = RadioGroup(this).apply {
            orientation = RadioGroup.HORIZONTAL
            gravity = Gravity.CENTER_HORIZONTAL

            val radioA = RadioButton(this@GraphicsActivity).apply {
                text = "a"
                id = R.id.radioA
            }

            val radioB = RadioButton(this@GraphicsActivity).apply {
                text = "b"
                id = R.id.radioB
            }

            val radioC = RadioButton(this@GraphicsActivity).apply {
                text = "c"
                id = R.id.radioC
            }

            addView(radioA)
            addView(radioB)
            addView(radioC)

            setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.radioA -> onOptionAClicked()
                    R.id.radioB -> onOptionBClicked()
                    R.id.radioC -> onOptionCClicked()
                }
            }
        }

        // Create the GLSurfaceView
        gLView = GraphicsSurfaceView(this)

        // Create the SeekBar for the bottom slider (sliderB)
        val sliderB = SeekBar(this).apply {
            max = 16
            id = R.id.seekB
            setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    Log.d("GraphicsActivity", "Slider B value: $progress")
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // Do something when tracking starts
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // Do something when tracking stops
                }
            })
        }

        // Add the SeekBar (sliderA), RadioGroup, GLSurfaceView, and another SeekBar (sliderB) to the main layout
        mainLayout.addView(sliderA)
        mainLayout.addView(segmentedPicker)
        mainLayout.addView(gLView, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            1.0f
        ))
        mainLayout.addView(sliderB)

        // Set the main layout as the content view
        setContentView(mainLayout)

        if (isTablet(this)) {
            bloomPasses = 8
            stereoSpreadBase = 6.0f
            stereoSpreadMax = 16.0f
        }
    }

    private fun onOptionAClicked() {
        // Custom function for option A click
        Log.d("GraphicsActivity", "Option A clicked")
        // Add your custom logic here
    }

    private fun onOptionBClicked() {
        // Custom function for option B click
        Log.d("GraphicsActivity", "Option B clicked")
        // Add your custom logic here
    }

    private fun onOptionCClicked() {
        // Custom function for option C click
        Log.d("GraphicsActivity", "Option C clicked")
        // Add your custom logic here
    }

    override fun onPause() {
        super.onPause()
        Log.d("GraphicsActivity", "onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d("GraphicsActivity", "onResume")
    }
}
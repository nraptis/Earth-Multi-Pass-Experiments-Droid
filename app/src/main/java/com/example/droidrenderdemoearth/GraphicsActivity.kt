package com.example.droidrenderdemoearth

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.graphics.Color
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.LifecycleOwner

class GraphicsActivity : AppCompatActivity() {

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
        val mainLayout = ConstraintLayout(this).apply {
            setBackgroundColor(Color.RED)
            id = View.generateViewId()  // Assign an ID for later use in constraints
        }

// Attach LifecycleOwner to ConstraintLayout
        //ViewTreeLifecycleOwner.set(mainLayout, this as LifecycleOwner)

        // Create a ComposeView to host the SegmentedControl
        val composeView = ComposeView(this).apply {
            id = View.generateViewId()
            setContent {
                SegmentedControl(
                    items = listOf("a", "b", "c"),
                    onItemSelection = { index ->
                        when (index) {
                            0 -> onOptionAClicked()
                            1 -> onOptionBClicked()
                            2 -> onOptionCClicked()
                        }
                    }
                )
            }
        }

        // Create the SeekBar for the top slider (sliderA)
        val sliderA = SeekBar(this).apply {
            max = 5
            id = View.generateViewId()
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

            id = View.generateViewId()
            setBackgroundColor(Color.BLUE)
            orientation = RadioGroup.HORIZONTAL
            gravity = Gravity.CENTER_HORIZONTAL

            val radioA = RadioButton(this@GraphicsActivity).apply {
                text = "a"
                id = View.generateViewId()
            }

            val radioB = RadioButton(this@GraphicsActivity).apply {
                text = "b"
                id = View.generateViewId()
            }

            val radioC = RadioButton(this@GraphicsActivity).apply {
                text = "c"
                id = View.generateViewId()
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
        gLView = GraphicsSurfaceView(this).apply {
            id = View.generateViewId()
        }

        // Create the SeekBar for the bottom slider (sliderB)
        val sliderB = SeekBar(this).apply {
            id = View.generateViewId()
            max = 16
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
        //mainLayout.addView(sliderA)

        mainLayout.addView(gLView)

        mainLayout.addView(composeView)

        //mainLayout.addView(sliderB)


        val constraintSet = ConstraintSet().apply {

            // Constrain height to be exactly 64dp
            constrainHeight(composeView.id, 120)

            // Constrain width to be at most 420dp, or match parent if less than 420dp
            constrainMaxWidth(composeView.id, 420)

            // Connect top, start, end to parent with margins
            connect(composeView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16)
            connect(composeView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16)
            connect(composeView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16)


            connect(gLView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16)
            connect(gLView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 16)
            connect(gLView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16)
            connect(gLView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16)
        }
        constraintSet.applyTo(mainLayout)

        /*
        val constraintSet = ConstraintSet().apply {
            //clone(mainLayout)
            connect(gLView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16)
            connect(gLView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 16)
            connect(gLView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16)
            connect(gLView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16)
        }
        constraintSet.applyTo(mainLayout)
        */

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
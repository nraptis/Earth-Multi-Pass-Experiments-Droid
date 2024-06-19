package com.example.droidrenderdemoearth

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log


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
        gLView = GraphicsSurfaceView(this)
        setContentView(gLView)

        if (isTablet(this)) {
            bloomPasses = 8
            stereoSpreadBase = 6.0f
            stereoSpreadMax = 16.0f
        }

    }

    override fun onPause() {
        super.onPause()
        // Code to execute when the activity is paused
        // For example, you might release resources or save data here
        Log.d("GraphicsActivity", "onPause")
    }

    override fun onResume() {
        super.onResume()
        // Code to execute when the activity is resumed
        // For example, you might re-initialize resources or update the UI here
        Log.d("GraphicsActivity", "onResume")
    }
}
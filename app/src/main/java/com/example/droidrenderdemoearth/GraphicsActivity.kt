package com.example.droidrenderdemoearth

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log


class GraphicsActivity : Activity() {

    private lateinit var gLView: GLSurfaceView


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gLView = GraphicsSurfaceView(this)
        setContentView(gLView)
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
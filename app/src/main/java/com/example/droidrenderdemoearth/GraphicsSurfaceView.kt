package com.example.droidrenderdemoearth

import android.content.Context
import android.opengl.GLSurfaceView
class GraphicsSurfaceView(var activity: GraphicsActivity?) : GLSurfaceView(activity) {

    private lateinit var renderer: GraphicsRenderer
    init {
        setEGLContextClientVersion(2)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Access width and height here
        val width = w
        val height = h

        print("SURFACE CHANGED: WIDTH: " + w)
        print("SURFACE CHANGED: HEOGHT: " + h)

        val earthScene = EarthScene(width, height, context, activity)

        renderer = GraphicsRenderer(earthScene, context, activity,this, width, height)
        setRenderer(renderer)
        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

    }

}
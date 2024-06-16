package com.example.droidrenderdemoearth

import android.opengl.GLES20
import java.nio.FloatBuffer

interface Uniforms {
    fun link(graphics: GraphicsLibrary?, shaderProgram: ShaderProgram?)
}





interface UniformsVertex : Uniforms {
    var projectionMatrix: Matrix
    var modelViewMatrix: Matrix
}

interface UniformsFragment : Uniforms {
    var red: Float
    var green: Float
    var blue: Float
    var alpha: Float
}

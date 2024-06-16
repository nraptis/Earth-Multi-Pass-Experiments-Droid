package com.example.droidrenderdemoearth

import kotlin.math.*

data class UniformsShapeFragment(
    override var red: Float = 1.0f,
    override var green: Float = 1.0f,
    override var blue: Float = 1.0f,
    override var alpha: Float = 1.0f
) : UniformsFragment {

    fun set(red: Float, green: Float, blue: Float) {
        this.red = red
        this.green = green
        this.blue = blue
        alpha = 1.0f
    }

    fun set(red: Float, green: Float, blue: Float, alpha: Float) {
        this.red = red
        this.green = green
        this.blue = blue
        this.alpha = alpha
    }

    override fun link(graphics: GraphicsLibrary?, shaderProgram: ShaderProgram?) {
        graphics?.let {_graphics ->
            _graphics.uniformsModulateColorSet(shaderProgram, red, green, blue, alpha)
        }
    }
}

data class UniformsShapeVertex(
    override var projectionMatrix: Matrix = Matrix(),
    override var modelViewMatrix: Matrix = Matrix()
) : UniformsVertex {

    override fun link(graphics: GraphicsLibrary?, shaderProgram: ShaderProgram?) {
        graphics?.let {_graphics ->
            _graphics.uniformsProjectionMatrixSet(shaderProgram, projectionMatrix)
            _graphics.uniformsModelViewMatrixSet(shaderProgram, modelViewMatrix)
        }
    }

}
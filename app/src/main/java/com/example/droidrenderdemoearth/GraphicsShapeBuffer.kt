package com.example.droidrenderdemoearth

import android.icu.text.Transliterator.Position
import java.nio.IntBuffer

//class GraphicsShapeBuffer {

class GraphicsShape2DBuffer: GraphicsShapeBuffer<Shape2DVertex>() {
}

class GraphicsShape3DBuffer: GraphicsShapeBuffer<Shape3DVertex>() {

}

open class GraphicsShapeBuffer<T>() where T : PositionConforming2D, T: FloatBufferable {

    var graphicsArrayBuffer: GraphicsArrayBuffer<T>? = null

    var graphics: GraphicsLibrary? = null
    var indexBuffer: IntBuffer? = null
    var indexCount = 0

    var projectionMatrix = Matrix()
    var modelViewMatrix = Matrix()

    var color = Color(1.0f, 1.0f, 1.0f, 1.0f)

    fun load(graphics: GraphicsLibrary?,
             graphicsArrayBuffer: GraphicsArrayBuffer<T>?,
             indices: IntArray) {
        this.graphics = graphics
        this.graphicsArrayBuffer = graphicsArrayBuffer
        indexBuffer = graphics?.indexBufferGenerate(indices)
        indexCount = indices.size
    }

    open fun bindAdditionalUniforms(shaderProgram: ShaderProgram?) {

    }

    open fun render(shaderProgram: ShaderProgram?) {
        shaderProgram?.let { _shaderProgram ->
            graphics?.let { _graphics ->
                _graphics.linkBufferToShaderProgram(_shaderProgram, graphicsArrayBuffer)
                _graphics.uniformsModulateColorSet(_shaderProgram, color)
                _graphics.uniformsProjectionMatrixSet(_shaderProgram, projectionMatrix)
                _graphics.uniformsModelViewMatrixSet(_shaderProgram, modelViewMatrix)
                bindAdditionalUniforms(shaderProgram)
                _graphics.drawTriangleStrips(indexBuffer, indexCount)
                _graphics.unlinkBufferFromShaderProgram (_shaderProgram)
            }
        }
    }
}

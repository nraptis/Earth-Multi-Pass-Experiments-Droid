package com.example.droidrenderdemoearth

import java.nio.IntBuffer

class GraphicsShape2DInstance: GraphicsShapeInstance<VertexShape2D>(arrayOf(
    VertexShape2D(0.0f, 0.0f),
    VertexShape2D(256.0f, 0.0f),
    VertexShape2D(0.0f, 256.0f),
    VertexShape2D(256.0f, 256.0f))) {
}

class GraphicsShape3DInstance: GraphicsShapeInstance<VertexShape3D>(arrayOf(
    VertexShape3D(0.0f, 0.0f, 0.0f),
    VertexShape3D(256.0f, 0.0f, 0.0f),
    VertexShape3D(0.0f, 256.0f, 0.0f),
    VertexShape3D(256.0f, 256.0f, 0.0f))) {
}

open class GraphicsShapeInstance<T>(val vertexArray: Array<T>) where T : Positionable2D, T: FloatBufferable {

    var graphicsArrayBuffer = GraphicsArrayBuffer<T>()

    var graphics: GraphicsLibrary? = null

    val indices = intArrayOf(0, 1, 2, 3)
    var indexBuffer: IntBuffer? = null

    var projectionMatrix = Matrix()
    var modelViewMatrix = Matrix()

    var color = Color(1.0f, 1.0f, 1.0f, 1.0f)

    var isVertexBufferDirty = true

    fun setVertexArray(vertexArray: Array<T>) {
        this.vertexArray[0] = vertexArray[0]
        this.vertexArray[1] = vertexArray[1]
        this.vertexArray[2] = vertexArray[2]
        this.vertexArray[3] = vertexArray[3]
        //
        isVertexBufferDirty = true
    }

    fun setPositionFrame(x: Float, y: Float,
                         width: Float, height: Float) {
        setPositionQuad(x, y,
            x + width, y,
            x, y + height,
            x + width, y + height)
    }

    fun setPositionQuad(x1: Float, y1: Float,
                        x2: Float, y2: Float,
                        x3: Float, y3: Float,
                        x4: Float, y4: Float) {
        vertexArray[0].x = x1
        vertexArray[0].y = y1
        vertexArray[1].x = x2
        vertexArray[1].y = y2
        vertexArray[2].x = x3
        vertexArray[2].y = y3
        vertexArray[3].x = x4
        vertexArray[3].y = y4
        isVertexBufferDirty = true
    }

    fun load(graphics: GraphicsLibrary?) {
        this.graphics = graphics
        indexBuffer = graphics?.indexBufferGenerate(indices)
        graphicsArrayBuffer.load(graphics, vertexArray)
        isVertexBufferDirty = false
    }

    open fun bindAdditionalUniforms(shaderProgram: ShaderProgram?) {

    }

    open fun render(shaderProgram: ShaderProgram?) {

        shaderProgram?.let { _shaderProgram ->
            graphics?.let { _graphics ->

                if (isVertexBufferDirty) {
                    graphicsArrayBuffer.write(this.vertexArray)
                    isVertexBufferDirty = false
                }

                _graphics.linkBufferToShaderProgram(_shaderProgram, graphicsArrayBuffer)

                _graphics.uniformsModulateColorSet(_shaderProgram, color)
                _graphics.uniformsProjectionMatrixSet(_shaderProgram, projectionMatrix)
                _graphics.uniformsModelViewMatrixSet(_shaderProgram, modelViewMatrix)

                bindAdditionalUniforms(shaderProgram)

                _graphics.drawTriangleStrips(indexBuffer, 4)
                _graphics.unlinkBufferFromShaderProgram (_shaderProgram)
            }
        }
    }
}

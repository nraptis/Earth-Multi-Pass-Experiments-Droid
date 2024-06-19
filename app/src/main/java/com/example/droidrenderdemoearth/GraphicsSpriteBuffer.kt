package com.example.droidrenderdemoearth

import java.nio.IntBuffer


class GraphicsSprite2DBuffer: GraphicsSpriteBuffer<Sprite2DVertex>() {
}

class GraphicsSprite3DBuffer: GraphicsSpriteBuffer<Sprite3DVertex>() {

}

open class GraphicsSpriteBuffer<T>() where T : PositionConforming2D, T: TextureCoordinateConforming, T: FloatBufferable {

    var graphicsArrayBuffer: GraphicsArrayBuffer<T>? = null

    var graphics: GraphicsLibrary? = null
    var indexBuffer: IntBuffer? = null
    var indexCount = 0

    var projectionMatrix = Matrix()
    var modelViewMatrix = Matrix()

    var color = Color(1.0f, 1.0f, 1.0f, 1.0f)

    var texture: GraphicsTexture? = null

    fun load(
        graphics: GraphicsLibrary?,
        graphicsArrayBuffer: GraphicsArrayBuffer<T>?,
        indices: IntArray,
        texture: GraphicsTexture?
    ) {
        this.graphics = graphics
        this.graphicsArrayBuffer = graphicsArrayBuffer
        this.texture = texture
        indexBuffer = graphics?.bufferIndexGenerate(indices)
        indexCount = indices.size
    }

    open fun bindAdditionalUniforms(shaderProgram: ShaderProgram?) {

    }

    open fun render(shaderProgram: ShaderProgram?) {
        shaderProgram?.let { _shaderProgram ->
            graphics?.let { _graphics ->
                _graphics.linkBufferToShaderProgram(_shaderProgram, graphicsArrayBuffer)
                _graphics.uniformsTextureSet(_shaderProgram, texture)
                _graphics.uniformsModulateColorSet(_shaderProgram, color)
                _graphics.uniformsProjectionMatrixSet(_shaderProgram, projectionMatrix)
                _graphics.uniformsModelViewMatrixSet(_shaderProgram, modelViewMatrix)
                bindAdditionalUniforms(shaderProgram)

                _graphics.drawTriangleStrips(indexBuffer, indexCount)

                _graphics.unlinkBufferFromShaderProgram(_shaderProgram)
            }
        }
    }
}
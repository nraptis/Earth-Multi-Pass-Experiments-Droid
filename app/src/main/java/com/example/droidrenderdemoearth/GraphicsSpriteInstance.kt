package com.example.droidrenderdemoearth

import java.nio.IntBuffer

class GraphicsSprite2DInstance: GraphicsSpriteInstance<VertexSprite2D>(arrayOf(
    VertexSprite2D(0.0f, 0.0f, 0.0f, 0.0f),
    VertexSprite2D(256.0f, 0.0f, 1.0f, 0.0f),
    VertexSprite2D(0.0f, 256.0f, 0.0f, 1.0f),
    VertexSprite2D(256.0f, 256.0f, 1.0f, 1.0f))) {
}

class GraphicsSprite3DInstance: GraphicsSpriteInstance<VertexSprite3D>(arrayOf(
    VertexSprite3D(0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
    VertexSprite3D(256.0f, 0.0f, 0.0f, 1.0f, 0.0f),
    VertexSprite3D(0.0f, 256.0f, 0.0f, 0.0f, 1.0f),
    VertexSprite3D(256.0f, 256.0f, 0.0f, 1.0f, 1.0f))) {
}

open class GraphicsSpriteInstance<T>(val vertexArray: Array<T>) where T : Positionable2D, T : Texturable2D, T: FloatBufferable {

    var graphicsArrayBuffer = GraphicsArrayBuffer<T>()

    var graphics: GraphicsLibrary? = null
    var texture: GraphicsTexture? = null

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

    fun setTextureCoordinateQuad(u1: Float, v1: Float,
                                 u2: Float, v2: Float,
                                 u3: Float, v3: Float,
                                 u4: Float, v4: Float) {
        vertexArray[0].u = u1
        vertexArray[0].v = v1
        vertexArray[1].u = u2
        vertexArray[1].v = v2
        vertexArray[2].u = u3
        vertexArray[2].v = v3
        vertexArray[3].u = u4
        vertexArray[3].v = v4
        isVertexBufferDirty = true
    }

    fun load(graphics: GraphicsLibrary?, texture: GraphicsTexture?) {
        this.graphics = graphics
        this.texture = texture
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

                _graphics.uniformsTextureSet(_shaderProgram, texture)
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

class GraphicsSpriteBlurInstance: GraphicsSpriteInstance<VertexSprite2D>(arrayOf(
    VertexSprite2D(0.0f, 0.0f, 0.0f, 0.0f),
    VertexSprite2D(256.0f, 0.0f, 1.0f, 0.0f),
    VertexSprite2D(0.0f, 256.0f, 0.0f, 1.0f),
    VertexSprite2D(256.0f, 256.0f, 1.0f, 1.0f))) {

    override fun bindAdditionalUniforms(shaderProgram: ShaderProgram?) {

        shaderProgram?.let { _shaderProgram ->
            graphics?.let { _graphics ->
                _graphics.uniformsTextureSizeSet(
                    _shaderProgram,
                    texture?.widthf ?: 32.0f,
                    texture?.heightf ?: 32.0f
                )
            }
        }
    }
}


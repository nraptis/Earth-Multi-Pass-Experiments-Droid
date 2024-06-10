package com.example.droidrenderdemoearth

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLES20

class GraphicsRenderTarget<T>(var frameBufferSpriteInstance: GraphicsSpriteInstance<T>) where T : Positionable2D, T : Texturable2D, T: FloatBufferable {

    var width = 0
    var height = 0

    var graphics: GraphicsLibrary? = null
    var graphicsPipeline: GraphicsPipeline? = null

    var frameBufferTextureIndex = -1
    var frameBufferIndex = -1
    var renderBufferIndex = -1
    val frameBufferTexture = GraphicsTexture()

    fun load(graphics: GraphicsLibrary?, graphicsPipeline: GraphicsPipeline?, width: Int, height: Int) {
        this.graphics = graphics
        this.graphicsPipeline = graphicsPipeline
        this.width = width
        this.height = height
        graphics?.let { _graphics ->
            frameBufferTextureIndex = _graphics.textureGenerate(width, height)
            val frameBufferHandle = IntArray(1)
            GLES20.glGenFramebuffers(1, frameBufferHandle, 0)
            frameBufferIndex = frameBufferHandle[0]
            GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBufferIndex)
            GLES20.glFramebufferTexture2D(
                GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D, frameBufferTextureIndex,0)
            frameBufferTexture.load(graphics, frameBufferTextureIndex, width, height)
            frameBufferSpriteInstance.load(graphics, frameBufferTexture)

            frameBufferSpriteInstance.setPositionFrame(0.0f, 0.0f, width.toFloat(), height.toFloat())
            frameBufferSpriteInstance.projectionMatrix.ortho(width, height)
            frameBufferSpriteInstance.modelViewMatrix.reset()
        }
    }

    fun render() {
        graphicsPipeline?.let { _graphicsPipeline ->
            frameBufferSpriteInstance.render(_graphicsPipeline.programSprite2D)
        }
    }

    fun render(width: Int, height: Int, scale: Int) {
        graphicsPipeline?.let { _graphicsPipeline ->
            frameBufferSpriteInstance.projectionMatrix.ortho(width, height)
            frameBufferSpriteInstance.modelViewMatrix.reset()
            frameBufferSpriteInstance.setPositionFrame(0.0f, 0.0f, (width / 2).toFloat(), (height / 2).toFloat())
            frameBufferSpriteInstance.render(_graphicsPipeline.programSprite2D)
        }
    }

}
package com.example.droidrenderdemoearth

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLES20

class GraphicsRenderTarget {

    var width = 0
    var height = 0

    var graphics: GraphicsLibrary? = null
    var graphicsPipeline: GraphicsPipeline? = null

    var frameBufferTextureIndex = -1
    var frameBufferIndex = -1
    var renderBufferIndex = -1
    val frameBufferTexture = GraphicsTexture()
    var frameBufferSpriteInstance = GraphicsSprite2DInstance()

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


            //frameBufferSpriteInstance.modelViewMatrix.scale(1.0f, -1.0f, 1.0f)
            //frameBufferSpriteInstance.modelViewMatrix.translation(width / 2.0f, height / 2.0f, 0.0f)


        }
    }

    fun render() {
        graphicsPipeline?.let { _graphicsPipeline ->
            frameBufferSpriteInstance.render(_graphicsPipeline.programSprite2D)
        }
    }

    /*

    var frameBufferIndex: Int
    var width: Int
    var height: Int
    val texture: GraphicsTexture

    constructor(context: Context?, graphics: GraphicsLibrary?, fileName: String) : this(graphics,
        FileUtils.readFileFromAssetAsBitmap(context, fileName), fileName) {

    }

    constructor(graphics: GraphicsLibrary?, bitmap: Bitmap?, fileName: String? = null) {
        this.graphics = graphics
        this.fileName = fileName

        width = 0
        height = 0
        textureIndex = -1

        graphics?.let { _graphics ->
            bitmap?.let { _bitmap ->
                width = _bitmap.width
                height = _bitmap.height
                textureIndex = _graphics.textureGenerate(_bitmap)
            }
        }
    }

    */
}
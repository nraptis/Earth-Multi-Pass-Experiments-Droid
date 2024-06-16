package com.example.droidrenderdemoearth

class Sprite {

    var texture: GraphicsTexture? = null

    var width: Float = 0.0f
    var width2: Float = 0.0f

    var height: Float = 0.0f
    var height2: Float = 0.0f

    var scaleFactor: Float = 1.0f

    var startX = -64.0f
    var startY = -64.0f
    var endX = 64.0f
    var endY = 64.0f

    var startU = 0.0f
    var startV = 0.0f
    var endU = 1.0f
    var endV = 1.0f

    fun load(graphics: GraphicsLibrary?, texture: GraphicsTexture?) {
        load(graphics, texture, 1.0f)
    }

    fun load(graphics: GraphicsLibrary?, texture: GraphicsTexture?, scaleFactor: Float) {
        if (texture != null) {
            this.texture = texture
            this.scaleFactor = scaleFactor

            width = texture.width.toFloat()
            height = texture.height.toFloat()

            if (scaleFactor > 1.0f) {
                width = ((width / scaleFactor) + 0.5f).toInt().toFloat()
                height = ((height / scaleFactor) + 0.5f).toInt().toFloat()
            }

            width2 = ((width * 0.5f) + 0.5f).toInt().toFloat()
            height2 = ((height * 0.5f) + 0.5f).toInt().toFloat()
            val _width_2 = -width2
            val _height_2 = -height2

            startX = _width_2
            startY = _height_2
            endX = width2
            endY = height2

            startU = 0.0f
            startV = 0.0f
            endU = 1.0f
            endV = 1.0f

        } else {
            this.texture = null
            width = 0.0f
            height = 0.0f
            width2 = 0.0f
            height2 = 0.0f
            this.scaleFactor = 1.0f
        }
    }

    fun setFrame(x: Float, y: Float, width: Float, height: Float) {
        startX = x
        startY = y
        endX = x + width
        endY = y + height
    }
}
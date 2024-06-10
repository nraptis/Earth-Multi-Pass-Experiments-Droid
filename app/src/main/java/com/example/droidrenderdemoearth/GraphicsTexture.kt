package com.example.droidrenderdemoearth

import android.content.Context
import android.graphics.Bitmap

class GraphicsTexture {

    var graphics: GraphicsLibrary? = null
    var textureIndex = -1
    var width = 0
    var widthf = 0.0f

    var height = 0
    var heightf = 0.0f

    var fileName: String? = null

    fun load(context: Context?, graphics: GraphicsLibrary?, fileName: String) {
        val bitmap = FileUtils.readFileFromAssetAsBitmap(context, fileName)
        load(graphics, bitmap, fileName)
    }

    fun load(graphics: GraphicsLibrary?, bitmap: Bitmap?, fileName: String? = null) {
        this.graphics = graphics
        this.fileName = fileName
        width = 0
        height = 0
        widthf = 0.0f
        heightf = 0.0f
        textureIndex = -1
        graphics?.let { _graphics ->
            bitmap?.let { _bitmap ->
                width = _bitmap.width
                height = _bitmap.height
                widthf = width.toFloat()
                heightf = height.toFloat()
                textureIndex = _graphics.textureGenerate(_bitmap)
            }
        }
    }

    fun load(graphics: GraphicsLibrary?, width: Int, height: Int, fileName: String? = null) {
        this.graphics = graphics
        this.fileName = fileName
        this.width = width
        this.height = height
        widthf = width.toFloat()
        heightf = height.toFloat()
        textureIndex = -1
        graphics?.let {
            textureIndex = it.textureGenerate(width, height)
        }
    }

    fun load(graphics: GraphicsLibrary?, textureIndex: Int, width: Int, height: Int, fileName: String? = null) {
        this.graphics = graphics
        this.fileName = fileName
        this.width = width
        this.height = height
        widthf = width.toFloat()
        heightf = height.toFloat()
        this.textureIndex = textureIndex
    }

}
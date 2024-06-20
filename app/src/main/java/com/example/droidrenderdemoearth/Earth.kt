package com.example.droidrenderdemoearth

import android.content.Context

class Earth(var context: Context?,
            var graphics: GraphicsLibrary?,
            var graphicsPipeline: GraphicsPipeline?) {

    companion object {
        const val tileCountV = 64
        const val tileCountH = 64
    }

    var earthModelDataStrips: Array<EarthModelDataStrip>
    var width = 800.0f
    var height = 1200.0f

    var earthMap: Sprite? = null
    var lightMap: Sprite? = null

    init {
        //this.graphics = graphics
        //this.graphicsPipeline = graphicsPipeline

        earthModelDataStrips = Array<EarthModelDataStrip>(Earth.tileCountV) {
            EarthModelDataStrip(context, it + 1, graphics, graphicsPipeline)
        }
    }

    fun load(graphics: GraphicsLibrary?,
             graphicsPipeline: GraphicsPipeline?,
             earthMap: Sprite,
             lightMap: Sprite) {
        this.earthMap = earthMap
        this.lightMap = lightMap
        for (earthModelDataStrip in earthModelDataStrips) {
            earthModelDataStrip.load(graphics, earthMap, lightMap)
        }
    }

    fun updateStereo(radians: Float,
                     stereoSpreadBase: Float,
                     stereoSpreadMax: Float,
                     ticksConsumed: Int) {
        for (earthModelDataStrip in earthModelDataStrips) {
            earthModelDataStrip.updateStereo(radians,
                width,
                height,
                stereoSpreadBase,
                stereoSpreadMax,
                ticksConsumed)
        }
    }

    fun draw3DBloom(width: Int, height: Int) {
        for (earthModelDataStrip in earthModelDataStrips) {
            earthModelDataStrip.draw3DBloom(width, height)
        }
    }

    fun draw3D(width: Int, height: Int) {
        for (earthModelDataStrip in earthModelDataStrips) {
            earthModelDataStrip.draw3D(width, height)
        }
    }

}
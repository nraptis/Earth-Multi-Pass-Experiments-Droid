package com.example.droidrenderdemoearth

class Earth(var graphics: GraphicsLibrary?,
            var graphicsPipeline: GraphicsPipeline?) {

    var earthModelData: EarthModelData
    var earthModelDataStrips: Array<EarthModelDataStrip>
    var width = 800.0f
    var height = 1200.0f

    var earthMap: Sprite? = null
    var lightMap: Sprite? = null

    init {
        //this.graphics = graphics
        //this.graphicsPipeline = graphicsPipeline

        earthModelData = EarthModelData(graphics?.widthf ?: 320.0f,graphics?.heightf ?: 320.0f)

        //earthModelData = EarthModelData((graphics?.widthf ?: 320.0f) * 1.5f,(graphics?.heightf ?: 320.0f) * 2.0f)

        earthModelDataStrips = Array<EarthModelDataStrip>(EarthModelData.tileCountV) {
            EarthModelDataStrip(earthModelData, it + 1, graphics, graphicsPipeline)
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

    fun updateStereo(radians: Float, stereoSpreadBase: Float, stereoSpreadMax: Float) {
        for (earthModelDataStrip in earthModelDataStrips) {
            earthModelDataStrip.updateStereo(radians, width, height, stereoSpreadBase, stereoSpreadMax)
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
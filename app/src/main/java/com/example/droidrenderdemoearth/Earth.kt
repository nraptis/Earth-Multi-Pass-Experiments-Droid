package com.example.droidrenderdemoearth

class Earth(var graphics: GraphicsLibrary?,
            var graphicsPipeline: GraphicsPipeline?,
            var texture: GraphicsTexture?) {

    var earthModelData: EarthModelData
    var earthModelDataStrips: Array<EarthModelDataStrip>

    init {
        //this.graphics = graphics
        //this.graphicsPipeline = graphicsPipeline

        earthModelData = EarthModelData(graphics?.widthf ?: 320.0f,graphics?.heightf ?: 320.0f)

        //earthModelData = EarthModelData((graphics?.widthf ?: 320.0f) * 1.5f,(graphics?.heightf ?: 320.0f) * 2.0f)

        earthModelDataStrips = Array<EarthModelDataStrip>(EarthModelData.tileCountV) {
            EarthModelDataStrip(earthModelData, it + 1, graphics, graphicsPipeline, texture)
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
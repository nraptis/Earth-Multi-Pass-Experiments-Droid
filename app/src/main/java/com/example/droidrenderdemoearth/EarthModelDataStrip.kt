package com.example.droidrenderdemoearth

import android.content.Context
import android.opengl.GLES20
import java.nio.IntBuffer
import kotlin.random.Random

class EarthModelDataStrip( var context: Context?,
                           var indexV: Int,
                           var graphics: GraphicsLibrary?,
                           var graphicsPipeline: GraphicsPipeline?) {

    var earthMap: Sprite? = null
    var lightMap: Sprite? = null

    var bloomBuffer = IndexedShapeBuffer3D()
    var noLightBuffer = IndexedSpriteBuffer3D()

    fun load(graphics: GraphicsLibrary?,
              earthMap: Sprite,
              lightMap: Sprite) {

        this.earthMap = earthMap
        this.lightMap = lightMap

        bloomBuffer.load(graphics)

        noLightBuffer.load(graphics, earthMap)
    }

    init {

        //this.graphics = graphics
        //this.graphicsPipeline = graphicsPipeline

        noLightBuffer.primitiveType = GLES20.GL_TRIANGLE_STRIP
        bloomBuffer.primitiveType = GLES20.GL_TRIANGLE_STRIP

        val indexCount = (Earth.tileCountH + 1) * 2
        for (index in 0 until indexCount) {
            bloomBuffer.add(index)
            noLightBuffer.add(index)
        }

    }

    fun updateStereo(radians: Float, width: Float, height: Float, stereoSpreadBase: Float, stereoSpreadMax: Float, ticksConsumed: Int) {


        val radius: Float = if (GraphicsActivity.isTablet(context)) {
            minOf(width, height) * (0.5f * 0.75f)
        } else {
            minOf(width, height) * (0.5f * 0.85f)
        }


        bloomBuffer.reset()
        noLightBuffer.reset()

        val startRotationH = Math.pi
        val endRotationH = startRotationH + Math.pi2
        val startRotationV = Math.pi
        val endRotationV = 0.0f

        val percentV1 = (indexV - 1).toFloat() / Earth.tileCountV.toFloat()
        val percentV2 = indexV.toFloat() / Earth.tileCountV.toFloat()
        val angleV1 = startRotationV + (endRotationV - startRotationV) * percentV1
        val angleV2 = startRotationV + (endRotationV - startRotationV) * percentV2

        var indexH = 0
        while (indexH <= Earth.tileCountH) {
            val percentH = indexH.toFloat() / Earth.tileCountH.toFloat()
            val angleH = startRotationH + (endRotationH - startRotationH) * percentH - radians

            var point1 = Float3(0.0f, 1.0f, 0.0f)
            point1 = Math.rotateX(point1, angleV1)
            point1 = Math.rotateY(point1, angleH)

            var point2 = Float3(0.0f, 1.0f, 0.0f)
            point2 = Math.rotateX(point2, angleV2)
            point2 = Math.rotateY(point2, angleH)

            val vertexIndex1 = indexH shl 1
            val vertexIndex2 = vertexIndex1 + 1


            val shift1 = stereoSpreadBase + kotlin.math.abs((point1.z) * stereoSpreadMax)
            val shift2 = stereoSpreadBase + kotlin.math.abs((point2.z) * stereoSpreadMax)

            val x1 = point1.x * radius
            val y1 = point1.y * radius
            val z1 = point1.z * radius

            val x2 = point2.x * radius
            val y2 = point2.y * radius
            val z2 = point2.z * radius

            val u1 = percentH
            val v1 = percentV1

            val u2 = percentH
            val v2 = percentV2

            val normalX1 = point1.x
            val normalY1 = point1.y
            val normalZ1 = point1.z

            val normalX2 = point1.x
            val normalY2 = point1.y
            val normalZ2 = point1.z


            val r1 = (Random.nextFloat() * 0.25f + 0.75f)
            val g1 = (Random.nextFloat() * 0.25f + 0.75f)
            val b1 = (Random.nextFloat() * 0.25f + 0.75f)

            val r2 = (Random.nextFloat() * 0.25f + 0.75f)
            val g2 = (Random.nextFloat() * 0.25f + 0.75f)
            val b2 = (Random.nextFloat() * 0.25f + 0.75f)


            bloomBuffer.add(Shape3DVertex(x1, y1, z1))
            bloomBuffer.add(Shape3DVertex(x2, y2, z2))

            noLightBuffer.add(Sprite3DVertex(x1, y1, z1, u1, v1))
            noLightBuffer.add(Sprite3DVertex(x2, y2, z2, u2, v2))


            bloomBuffer.add(indexH * 2)
            bloomBuffer.add(indexH * 2 + 1)

            noLightBuffer.add(indexH * 2)
            noLightBuffer.add(indexH * 2 + 1)


            indexH++
        }



    }

    fun draw3DBloom(width: Int, height: Int) {


        bloomBuffer.projectionMatrix.ortho(width, height)
        bloomBuffer.modelViewMatrix.reset()
        bloomBuffer.modelViewMatrix.translate(width / 2.0f, height / 2.0f, 0.0f)

        bloomBuffer.render(graphicsPipeline?.programShape3D)

        /*
        surfaceSpriteBuffer.projectionMatrix.ortho(width, height)
        surfaceSpriteBuffer.modelViewMatrix.reset()
        surfaceSpriteBuffer.modelViewMatrix.translate(width / 2.0f, height / 2.0f, 0.0f)

        surfaceSpriteBuffer.render(graphicsPipeline?.programSprite3D)
        */

    }

    fun draw3D(width: Int, height: Int) {


        //surfaceSpriteBuffer.projectionMatrix.ortho(width, height)
        //surfaceSpriteBuffer.modelViewMatrix.reset()
        //surfaceSpriteBuffer.modelViewMatrix.translate(width / 2.0f, height / 2.0f, 0.0f)

        //surfaceSpriteBuffer.render(graphicsPipeline?.programSprite3D)

        noLightBuffer.projectionMatrix.ortho(width, height)
        noLightBuffer.modelViewMatrix.reset()
        noLightBuffer.modelViewMatrix.translate(width / 2.0f, height / 2.0f, 0.0f)

        noLightBuffer.render(graphicsPipeline?.programSprite3D)


    }

}
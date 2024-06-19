package com.example.droidrenderdemoearth

import android.content.Context
import android.opengl.GLES20
import java.nio.IntBuffer
import kotlin.random.Random

class EarthModelDataStrip( var context: Context?,
                           var earthModelData: EarthModelData?,
                           var indexV: Int,
                           var graphics: GraphicsLibrary?,
                           var graphicsPipeline: GraphicsPipeline?) {



    var earthMap: Sprite? = null
    var lightMap: Sprite? = null

    val shapeVertexArray: Array<Shape3DVertex>
    val shapeVertexBuffer: GraphicsArrayBuffer<Shape3DVertex>

    val spriteVertexArray: Array<Sprite3DVertex>
    val spriteVertexBuffer: GraphicsArrayBuffer<Sprite3DVertex>

    val indices: IntArray

    val bloomShapeBuffer = GraphicsShapeBuffer<Shape3DVertex>()

    val surfaceSpriteBuffer = GraphicsSprite3DBuffer()

    var noLightBuffer = IndexedSpriteBuffer3D()

    fun load(graphics: GraphicsLibrary?,
              earthMap: Sprite,
              lightMap: Sprite) {

        this.earthMap = earthMap
        this.lightMap = lightMap

        noLightBuffer.load(graphics, earthMap)
    }

    init {

        //this.graphics = graphics
        //this.graphicsPipeline = graphicsPipeline

        noLightBuffer.primitiveType = GLES20.GL_TRIANGLE_STRIP

        val indexCount = (EarthModelData.tileCountH + 1) * 2
        indices = IntArray(indexCount) { it }

        for (index in 0 until indexCount) {
            noLightBuffer.add(index)
        }

        shapeVertexArray = Array(indexCount) {
            Shape3DVertex(0.0f, 0.0f, 0.0f)
        }

        spriteVertexArray = Array(indexCount) {
            Sprite3DVertex(0.0f, 0.0f, 0.0f, 0.0f, 0.0f)
        }

        earthModelData?.let { _earthModelData ->
            for (indexH in 0 .. EarthModelData.tileCountH) {

                val vertexIndex1 = indexH * 2
                val vertexIndex2 = vertexIndex1 + 1

                val vertex1 = shapeVertexArray[vertexIndex1]
                val vertex2 = shapeVertexArray[vertexIndex2]

                vertex1.x = _earthModelData.points[indexH][indexV - 1].x
                vertex1.y = _earthModelData.points[indexH][indexV - 1].y
                vertex1.z = _earthModelData.points[indexH][indexV - 1].z

                vertex2.x = _earthModelData.points[indexH][indexV].x
                vertex2.y = _earthModelData.points[indexH][indexV].y
                vertex2.z = _earthModelData.points[indexH][indexV].z


                //spriteVertexArray
            }

            for (indexH in 0 .. EarthModelData.tileCountH) {

                val vertexIndex1 = indexH * 2
                val vertexIndex2 = vertexIndex1 + 1

                val vertex1 = spriteVertexArray[vertexIndex1]
                val vertex2 = spriteVertexArray[vertexIndex2]

                vertex1.x = _earthModelData.points[indexH][indexV - 1].x
                vertex1.y = _earthModelData.points[indexH][indexV - 1].y
                vertex1.z = _earthModelData.points[indexH][indexV - 1].z

                vertex1.u = _earthModelData.textureCoords[indexH][indexV - 1].x
                vertex1.v = _earthModelData.textureCoords[indexH][indexV - 1].y

                vertex2.x = _earthModelData.points[indexH][indexV].x
                vertex2.y = _earthModelData.points[indexH][indexV].y
                vertex2.z = _earthModelData.points[indexH][indexV].z

                vertex2.u = _earthModelData.textureCoords[indexH][indexV].x
                vertex2.v = _earthModelData.textureCoords[indexH][indexV].y


                //noLightBuffer.add(Sprite3DVertex(vertex1.x, vertex1.y, vertex1.z, vertex1.u, vertex1.v))
                //noLightBuffer.add(Sprite3DVertex(vertex2.x, vertex2.y, vertex2.z, vertex2.u, vertex2.v))

            }
        }

        shapeVertexBuffer = GraphicsArrayBuffer()
        shapeVertexBuffer.load(graphics, shapeVertexArray)

        spriteVertexBuffer = GraphicsArrayBuffer()
        spriteVertexBuffer.load(graphics, spriteVertexArray)

        bloomShapeBuffer.color.red = 0.6f
        bloomShapeBuffer.color.green = 0.75f
        bloomShapeBuffer.color.blue = 1.0f
        bloomShapeBuffer.color.alpha = 1.0f
        bloomShapeBuffer.load(graphics, shapeVertexBuffer, indices)

        //surfaceSpriteBuffer.load(graphics, spriteVertexBuffer, indices, earthMap)
    }

    fun updateStereo(radians: Float, width: Float, height: Float, stereoSpreadBase: Float, stereoSpreadMax: Float, ticksConsumed: Int) {


        val radius: Float = if (GraphicsActivity.isTablet(context)) {
            minOf(width, height) * (0.5f * 0.75f)
        } else {
            minOf(width, height) * (0.5f * 0.85f)
        }

        noLightBuffer.reset()

        val startRotationH = Math.pi
        val endRotationH = startRotationH + Math.pi2
        val startRotationV = Math.pi
        val endRotationV = 0.0f

        val percentV1 = (indexV - 1).toFloat() / EarthModelData.tileCountV.toFloat()
        val percentV2 = indexV.toFloat() / EarthModelData.tileCountV.toFloat()
        val angleV1 = startRotationV + (endRotationV - startRotationV) * percentV1
        val angleV2 = startRotationV + (endRotationV - startRotationV) * percentV2

        var indexH = 0
        while (indexH <= EarthModelData.tileCountH) {
            val percentH = indexH.toFloat() / EarthModelData.tileCountH.toFloat()
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


            noLightBuffer.add(Sprite3DVertex(x1, y1, z1, u1, v1))
            noLightBuffer.add(Sprite3DVertex(x2, y2, z2, u2, v2))

            noLightBuffer.add(indexH * 2)
            noLightBuffer.add(indexH * 2 + 1)


            indexH++
        }



    }

    fun draw3DBloom(width: Int, height: Int) {

        //shapeVertexBuffer

        bloomShapeBuffer.projectionMatrix.ortho(width, height)
        bloomShapeBuffer.modelViewMatrix.reset()
        bloomShapeBuffer.modelViewMatrix.translate(width / 2.0f, height / 2.0f, 0.0f)

        bloomShapeBuffer.render(graphicsPipeline?.programShape3D)


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

        println("d3d?")

        noLightBuffer.projectionMatrix.ortho(width, height)
        noLightBuffer.modelViewMatrix.reset()
        noLightBuffer.modelViewMatrix.translate(width / 2.0f, height / 2.0f, 0.0f)

        noLightBuffer.render(graphicsPipeline?.programSprite3D)


    }

}
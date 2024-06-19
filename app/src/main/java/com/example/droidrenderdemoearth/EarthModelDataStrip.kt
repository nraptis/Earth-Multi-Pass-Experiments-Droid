package com.example.droidrenderdemoearth

import android.opengl.GLES20
import java.nio.IntBuffer

class EarthModelDataStrip(var earthModelData: EarthModelData?,
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

    var iddq = IndexedSpriteBuffer3D()

    fun  load(graphics: GraphicsLibrary?,
              earthMap: Sprite,
              lightMap: Sprite) {

        this.earthMap = earthMap
        this.lightMap = lightMap

        iddq.load(graphics, earthMap)
    }

    init {

        //this.graphics = graphics
        //this.graphicsPipeline = graphicsPipeline

        iddq.primitiveType = GLES20.GL_TRIANGLE_STRIP

        val indexCount = (EarthModelData.tileCountH + 1) * 2
        indices = IntArray(indexCount) { it }

        for (index in 0 until indexCount) {
            iddq.add(index)
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


                iddq.add(Sprite3DVertex(vertex1.x, vertex1.y, vertex1.z, vertex1.u, vertex1.v))
                iddq.add(Sprite3DVertex(vertex2.x, vertex2.y, vertex2.z, vertex2.u, vertex2.v))

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

    fun updateStereo(radians: Float, width: Float, height: Float, stereoSpreadBase: Float, stereoSpreadMax: Float) {


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

        iddq.projectionMatrix.ortho(width, height)
        iddq.modelViewMatrix.reset()
        iddq.modelViewMatrix.translate(width / 2.0f, height / 2.0f, 0.0f)

        iddq.render(graphicsPipeline?.programSprite3D)


    }

}
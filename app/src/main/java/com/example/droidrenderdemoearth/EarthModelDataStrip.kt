package com.example.droidrenderdemoearth

import java.nio.IntBuffer

class EarthModelDataStrip(var earthModelData: EarthModelData?,
                          var indexV: Int,
                          var graphics: GraphicsLibrary?,
                          var graphicsPipeline: GraphicsPipeline?,
                            var texture: GraphicsTexture?) {

    val shapeVertexArray: Array<Shape3DVertex>
    val shapeVertexBuffer: GraphicsArrayBuffer<Shape3DVertex>

    val spriteVertexArray: Array<Sprite3DVertex>
    val spriteVertexBuffer: GraphicsArrayBuffer<Sprite3DVertex>

    val indices: IntArray

    val bloomShapeBuffer = GraphicsShapeBuffer<Shape3DVertex>()

    val surfaceSpriteBuffer = GraphicsSprite3DBuffer()



    init {

        //this.graphics = graphics
        //this.graphicsPipeline = graphicsPipeline

        val indexCount = (EarthModelData.tileCountH + 1) * 2
        indices = IntArray(indexCount) { it }

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

        surfaceSpriteBuffer.load(graphics, spriteVertexBuffer, indices, texture)
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


        surfaceSpriteBuffer.projectionMatrix.ortho(width, height)
        surfaceSpriteBuffer.modelViewMatrix.reset()
        surfaceSpriteBuffer.modelViewMatrix.translate(width / 2.0f, height / 2.0f, 0.0f)

        surfaceSpriteBuffer.render(graphicsPipeline?.programSprite3D)

    }

}
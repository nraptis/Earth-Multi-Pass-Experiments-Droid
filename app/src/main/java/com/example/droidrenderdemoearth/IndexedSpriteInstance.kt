package com.example.droidrenderdemoearth

import java.nio.FloatBuffer
import java.nio.IntBuffer

fun FloatBuffer?.toFloatArray(): FloatArray? {
    return this?.let { buffer ->
        buffer.position(0)
        val array = FloatArray(buffer.remaining())
        buffer.get(array)
        buffer.position(0)
        array
    }
}

fun IntBuffer?.toIntArray(): IntArray? {
    return this?.let { buffer ->
        buffer.position(0)
        val array = IntArray(buffer.remaining())
        buffer.get(array)
        buffer.position(0)
        array
    }
}

class IndexedSpriteInstance<NodeType>(
    uniformsVertex: UniformsVertex,
    uniformsFragment: UniformsFragment
) : IndexedInstance<NodeType>(uniformsVertex, uniformsFragment)
        where NodeType : PositionConforming2D, NodeType : TextureCoordinateConforming, NodeType : FloatBufferable {

    constructor(

        uniformsVertex: UniformsVertex,
        uniformsFragment: UniformsFragment,
        node1: NodeType,
        node2: NodeType,
        node3: NodeType,
        node4: NodeType
    ) : this(
        uniformsVertex,
        uniformsFragment
    ) {
        vertices = listOf(node1, node2, node3, node4)
    }

    var sprite: Sprite? = null

    fun load(graphics: GraphicsLibrary?, sprite: Sprite?) {
        load(graphics)
        this.sprite = sprite
        sprite?.let {
            setTextureCoordFrame(it.startU, it.startV, it.endU, it.endV)
        }
    }

    override fun render(shaderProgram: ShaderProgram?) {
        shaderProgram?.let { _shaderProgram ->
            graphics?.let { _graphics ->

                setPositionFrame(0.0f, 0.0f, 600.0f, 700.0f)
                projectionMatrix.ortho(500.0f, 500.0f)
                modelViewMatrix.reset()
                red = 1.0f
                green = 0.5f
                blue = 0.5f
                alpha = 1.0f

                println("Indices: " + indices)
                println("indexBuffer: " + indexBuffer)

                val intz = indexBuffer.toIntArray()
                println("intz:")
                intz?.forEach { println(it) }


                val floatz = vertexBuffer.toFloatArray()

                println("floatz = ")
                floatz?.forEach { println(it) }

                println("vertexBufferIndex = " + vertexBufferIndex)


                //if (isVertexBufferDirty) {
                    writeVertexBuffer()
                    isVertexBufferDirty = false
                //}

                _graphics.linkBufferToShaderProgram(_shaderProgram, vertexBufferIndex)


                _graphics.uniformsTextureSet(_shaderProgram, sprite)


                _graphics.uniformsModulateColorSet(_shaderProgram, red, green, blue, alpha)
                _graphics.uniformsProjectionMatrixSet(_shaderProgram, projectionMatrix)
                _graphics.uniformsModelViewMatrixSet(_shaderProgram, modelViewMatrix)

                //bindAdditionalUniforms(shaderProgram)

                _graphics.drawTriangleStrips(indexBuffer, 4)
                _graphics.unlinkBufferFromShaderProgram (_shaderProgram)
            }
        }
    }

}
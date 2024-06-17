package com.example.droidrenderdemoearth

import java.nio.FloatBuffer
import java.nio.IntBuffer
import kotlin.random.Random

open class IndexedSpriteInstance<NodeType>(
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

                if (isVertexBufferDirty) {
                    writeVertexBuffer()
                    isVertexBufferDirty = false
                }

                _graphics.linkBufferToShaderProgram(_shaderProgram, vertexBufferIndex)

                _graphics.uniformsTextureSet(_shaderProgram, sprite)

                uniformsVertex.link(graphics, shaderProgram)
                uniformsFragment.link(graphics, shaderProgram)

                _graphics.drawTriangleStrips(indexBuffer, 4)
                _graphics.unlinkBufferFromShaderProgram (_shaderProgram)
            }
        }
    }
}

class IndexedSpriteInstance2D: IndexedSpriteInstance<Sprite2DVertex>(UniformsSpriteVertex(),
    UniformsSpriteFragment(),
    Sprite2DVertex(-128.0f, -128.0f, 0.0f, 0.0f),
    Sprite2DVertex(128.0f, -128.0f, 1.0f, 0.0f),
    Sprite2DVertex(-128.0f, 128.0f, 0.0f, 1.0f),
    Sprite2DVertex(128.0f, 128.0f, 1.0f, 1.0f))

class IndexedSpriteInstance3D: IndexedSpriteInstance<Sprite3DVertex>(UniformsSpriteVertex(),
    UniformsSpriteFragment(),
    Sprite3DVertex(-128.0f, -128.0f, 0.0f,0.0f, 0.0f),
    Sprite3DVertex(128.0f, -128.0f, 0.0f,1.0f, 0.0f),
    Sprite3DVertex(-128.0f, 128.0f, 0.0f,0.0f, 1.0f),
    Sprite3DVertex(128.0f, 128.0f, 0.0f,1.0f, 1.0f))
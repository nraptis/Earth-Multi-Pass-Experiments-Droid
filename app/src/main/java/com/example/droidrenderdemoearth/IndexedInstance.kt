package com.example.droidrenderdemoearth

import java.nio.FloatBuffer
import java.nio.IntBuffer

open class IndexedInstance<NodeType>(
    node1: NodeType,
    node2: NodeType,
    node3: NodeType,
    node4: NodeType,
    uniformsVertex: UniformsVertex,
    uniformsFragment: UniformsFragment
) : IndexedInstanceable<NodeType>
        where NodeType : PositionConforming2D, NodeType : FloatBufferable {

    override var graphics: GraphicsLibrary? = null
    override var vertices: MutableList<NodeType> = mutableListOf(node1, node2, node3, node4)

    override var uniformsVertex: UniformsVertex = uniformsVertex
    override var uniformsFragment: UniformsFragment = uniformsFragment

    override var indices: MutableList<Int> = mutableListOf(0, 1, 2, 3)
    override var indexBuffer: IntBuffer? = null

    override var vertexBuffer: FloatBuffer? = null
    override var vertexBufferIndex: Int = 0

    override var isVertexBufferDirty: Boolean = false

    override fun link(shaderProgram: ShaderProgram?) {
        // Link the render encoder and pipeline state
    }

    override fun render(shaderProgram: ShaderProgram?) {

    }

    // Extension function to set position frame
    fun setPositionFrame(x: Float, y: Float, width: Float, height: Float) {
        setPositionQuad(x, y, x + width, y, x, y + height, x + width, y + height)
    }

    // Extension function to set position quad
    fun setPositionQuad(x1: Float, y1: Float, x2: Float, y2: Float) {
        setPositionQuad(x1, y1, x2, y1, x1, y2, x2, y2)
    }

    // Extension function to set position quad
    fun setPositionQuad(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float, x4: Float, y4: Float) {
        vertices?.let { _vertices ->

            if (_vertices[0].x != x1) {
                _vertices[0].x = x1
                isVertexBufferDirty = true
            }
            if (_vertices[1].x != x2) {
                _vertices[1].x = x2
                isVertexBufferDirty = true
            }
            if (_vertices[2].x != x3) {
                _vertices[2].x = x3
                isVertexBufferDirty = true
            }
            if (_vertices[3].x != x4) {
                _vertices[3].x = x4
                isVertexBufferDirty = true
            }
            if (_vertices[0].y != y1) {
                _vertices[0].y = y1
                isVertexBufferDirty = true
            }
            if (_vertices[1].y != y2) {
                _vertices[1].y = y2
                isVertexBufferDirty = true
            }
            if (_vertices[2].y != y3) {
                _vertices[2].y = y3
                isVertexBufferDirty = true
            }
            if (_vertices[3].y != y4) {
                _vertices[3].y = y4
                isVertexBufferDirty = true
            }
        }
    }
}

// Extension functions for setting texture coordinates, only available when Node is TextureCoordinateConforming
fun <Node> IndexedInstance<Node>.setTextureCoordFrame(
    startU: Float, startV: Float, endU: Float, endV: Float
) where Node : TextureCoordinateConforming, Node : PositionConforming2D, Node : FloatBufferable {
    setTextureCoordQuad(startU, startV, endU, startV, startU, endV, endU, endV)
}

fun <Node> IndexedInstance<Node>.setTextureCoordQuad(
    u1: Float, v1: Float, u2: Float, v2: Float, u3: Float, v3: Float, u4: Float, v4: Float
) where Node : TextureCoordinateConforming, Node : PositionConforming2D, Node : FloatBufferable {
    vertices?.let { _vertices ->
        if (_vertices[0].u != u1) {
            _vertices[0].u = u1
            isVertexBufferDirty = true
        }
        if (_vertices[1].u != u2) {
            _vertices[1].u = u2
            isVertexBufferDirty = true
        }
        if (_vertices[2].u != u3) {
            _vertices[2].u = u3
            isVertexBufferDirty = true
        }
        if (_vertices[3].u != u4) {
            _vertices[3].u = u4
            isVertexBufferDirty = true
        }
        if (_vertices[0].v != v1) {
            _vertices[0].v = v1
            isVertexBufferDirty = true
        }
        if (_vertices[1].v != v2) {
            _vertices[1].v = v2
            isVertexBufferDirty = true
        }
        if (_vertices[2].v != v3) {
            _vertices[2].v = v3
            isVertexBufferDirty = true
        }
        if (_vertices[3].v != v4) {
            _vertices[3].v = v4
            isVertexBufferDirty = true
        }
    }
}

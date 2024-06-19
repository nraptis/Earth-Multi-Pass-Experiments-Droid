package com.example.droidrenderdemoearth

import java.nio.FloatBuffer
import java.nio.IntBuffer

class IndexedBuffer<NodeType>(
    override var uniformsVertex: UniformsVertex,
    override var uniformsFragment: UniformsFragment
) : IndexedBufferable<NodeType> where NodeType : FloatBufferable, NodeType: PositionConforming2D {

    override var graphics: GraphicsLibrary? = null

    override var vertexBufferIndex: Int = -1

    override var vertices: MutableList<NodeType> = mutableListOf()
    override var vertexCount: Int = 0
    override var vertexCapacity: Int = 0

    override var indices: MutableList<Int> = mutableListOf()
    override var indexCount: Int = 0
    override var indexCapacity: Int = 0

    override var vertexBufferLength: Int = 0
    override var indexBufferLength: Int = 0

    override var indexBuffer: IntBuffer? = null
    override var vertexBuffer: FloatBuffer? = null

    var uniformsVertexBuffer: FloatBuffer? = null
    var uniformsFragmentBuffer: FloatBuffer? = null

    override var isVertexBufferDirty: Boolean = false
    var isIndexBufferDirty: Boolean = false
    var isUniformsVertexBufferDirty: Boolean = false
    var isUniformsFragmentBufferDirty: Boolean = false

    override fun link(shaderProgram: ShaderProgram?) {
        // Implementation for linking shader program
    }

    override fun render(shaderProgram: ShaderProgram?) {
        // Implementation for rendering
    }

}
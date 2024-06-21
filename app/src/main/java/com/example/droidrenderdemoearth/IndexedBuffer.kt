package com.example.droidrenderdemoearth

import android.opengl.GLES20
import java.nio.FloatBuffer
import java.nio.IntBuffer

open class IndexedBuffer<NodeType>(
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
    override var isIndexBufferDirty: Boolean = false

    override var primitiveType: Int = GLES20.GL_TRIANGLES

    var isUniformsVertexBufferDirty: Boolean = false
    var isUniformsFragmentBufferDirty: Boolean = false




    override fun link(shaderProgram: ShaderProgram?) {
        // Implementation for linking shader program
    }

    override fun render(shaderProgram: ShaderProgram?) {
        // Implementation for rendering
        graphics?.let { _graphics ->

            if (indexCount <= 0) { return }
            if (vertexCount <= 0) { return }

            if (isVertexBufferDirty) {
                writeVertexBuffer()
                isVertexBufferDirty = false
            }

            if (isIndexBufferDirty) {
                writeIndexBuffer()
                isIndexBufferDirty = false
            }

            vertexBuffer?.let { _vertexBuffer ->
                indexBuffer?.let { _indexBuffer ->

                    _graphics.linkBufferToShaderProgram(shaderProgram, vertexBufferIndex)

                    link(shaderProgram)

                    uniformsVertex.link(graphics, shaderProgram)
                    uniformsFragment.link(graphics, shaderProgram)

                    _graphics.drawPrimitives(_indexBuffer, primitiveType, indexCount)
                    _graphics.unlinkBufferFromShaderProgram (shaderProgram)
                }
            }
        }
    }
}
package com.example.droidrenderdemoearth

import java.nio.FloatBuffer
import java.nio.IntBuffer

interface IndexedDrawable<NodeType> where NodeType : FloatBufferable {

    var graphics: GraphicsLibrary?

    var uniformsVertex: UniformsVertex
    var uniformsFragment: UniformsFragment

    var indices: IntArray?
    var indexBuffer: IntBuffer?

    var vertices: List<NodeType>?
    var vertexBuffer: FloatBuffer?
    var vertexBufferIndex: Int

    var isVertexBufferDirty: Boolean

    fun link(shaderProgram: ShaderProgram?)

    fun setDirty(isVertexBufferDirty: Boolean) {
        this.isVertexBufferDirty = isVertexBufferDirty
    }

    fun render(shaderProgram: ShaderProgram?)


    var projectionMatrix: Matrix
        get() = uniformsVertex.projectionMatrix
        set(value) {
            uniformsVertex.projectionMatrix = value
        }

    var modelViewMatrix: Matrix
        get() = uniformsVertex.modelViewMatrix
        set(value) {
            uniformsVertex.modelViewMatrix = value
        }

    var red: Float
        get() = uniformsFragment.red
        set(value) {
            uniformsFragment.red = value
        }

    var green: Float
        get() = uniformsFragment.green
        set(value) {
            uniformsFragment.green = value
        }

    var blue: Float
        get() = uniformsFragment.blue
        set(value) {
            uniformsFragment.blue = value
        }

    var alpha: Float
        get() = uniformsFragment.alpha
        set(value) {
            uniformsFragment.alpha = value
        }
}
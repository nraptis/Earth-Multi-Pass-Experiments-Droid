package com.example.droidrenderdemoearth

import java.nio.FloatBuffer
import java.nio.IntBuffer

interface IndexedInstanceable<NodeType : FloatBufferable> : IndexedDrawable<NodeType> {

    fun load(graphics: GraphicsLibrary?) {
        this.graphics = graphics
        graphics?.let { _graphics ->
            indices?.let { _indices ->
                indexBuffer = _graphics.indexBufferGenerate(_indices)
            }

            vertices?.let { _vertices ->
                vertexBuffer = _graphics.floatBufferGenerate(_vertices)
                vertexBuffer?.let { _vertexBuffer ->

                    val size = _graphics.floatBufferSize(_vertices) * Float.SIZE_BYTES

                    vertexBufferIndex = _graphics.bufferArrayGenerate(size)
                    _graphics.bufferArrayWrite(vertexBufferIndex, size, _vertexBuffer)
                }
            }
        }
    }

    fun writeVertexBuffer() {
        graphics?.let { _graphics ->
            vertices?.let { _vertices ->
                vertexBuffer?.let { _vertexBuffer ->
                    _graphics.floatBufferWrite(_vertices, _vertexBuffer)
                    val size = _graphics.floatBufferSize(_vertices) * Float.SIZE_BYTES
                    _graphics.bufferArrayWrite(vertexBufferIndex, size, _vertexBuffer)
                }
            }
        }
    }

}

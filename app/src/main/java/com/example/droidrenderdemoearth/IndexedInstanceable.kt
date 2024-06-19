package com.example.droidrenderdemoearth

interface IndexedInstanceable<NodeType : FloatBufferable> : IndexedDrawable<NodeType> {

    fun load(graphics: GraphicsLibrary?) {
        this.graphics = graphics
        graphics?.let { _graphics ->

            indexBuffer = _graphics.bufferIndexGenerate(indices)

            vertexBuffer = _graphics.bufferFloatGenerate(vertices)
            vertexBuffer?.let { _vertexBuffer ->

                val size = _graphics.bufferFloatSize(vertices) * Float.SIZE_BYTES

                vertexBufferIndex = _graphics.bufferArrayGenerate()
                _graphics.bufferArrayWrite(vertexBufferIndex, size, _vertexBuffer)
            }

        }
    }

    fun writeVertexBuffer() {
        println("writeVertexBuffer =>")
        graphics?.let { _graphics ->
            vertices?.let { _vertices ->
                vertexBuffer?.let { _vertexBuffer ->
                    println("write vertice: " + _vertices)
                    _graphics.bufferFloatWrite(_vertices, _vertexBuffer)
                    val size = _graphics.bufferFloatSize(_vertices) * Float.SIZE_BYTES
                    _graphics.bufferArrayWrite(vertexBufferIndex, size, _vertexBuffer)
                }
            }
        }
    }

}

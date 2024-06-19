package com.example.droidrenderdemoearth

interface IndexedBufferable<NodeType : FloatBufferable> : IndexedDrawable<NodeType> {

    var vertexCount: Int
    var vertexCapacity: Int

    var indexCount: Int
    var indexCapacity: Int

    var vertexBufferLength: Int
    var indexBufferLength: Int

    fun reset() {
        vertexCount = 0
        indexCount = 0
        isVertexBufferDirty = true
    }

    fun add(vertex: NodeType) {
        if (vertexCount >= vertexCapacity) {
            val newSize = vertexCount + (vertexCount / 2) + 1
            reserveCapacityVertices(minimumCapacity = newSize, placeholder = vertex)
        }
        vertices[vertexCount] = vertex
        vertexCount += 1
        isVertexBufferDirty = true
    }

    fun add(vertex1: NodeType, vertex2: NodeType, vertex3: NodeType, vertex4: NodeType) {
        if ((vertexCount + 3) >= vertexCapacity) {
            val newSize = (vertexCount + 3) + ((vertexCount + 3) / 2) + 1
            reserveCapacityVertices(minimumCapacity = newSize, placeholder = vertex1)
        }
        vertices[vertexCount + 0] = vertex1
        vertices[vertexCount + 1] = vertex2
        vertices[vertexCount + 2] = vertex3
        vertices[vertexCount + 3] = vertex4
        vertexCount += 4
        isVertexBufferDirty = true
    }

    fun add(vertex1: NodeType, vertex2: NodeType, vertex3: NodeType) {
        if ((vertexCount + 2) >= vertexCapacity) {
            val newSize = (vertexCount + 2) + ((vertexCount + 2) / 2) + 1
            reserveCapacityVertices(minimumCapacity = newSize, placeholder = vertex1)
        }
        vertices[vertexCount + 0] = vertex1
        vertices[vertexCount + 1] = vertex2
        vertices[vertexCount + 2] = vertex3
        vertexCount += 3
        isVertexBufferDirty = true
    }

    fun add(index: Int) {
        if (indexCount >= indexCapacity) {
            val newSize = indexCount + (indexCount / 2) + 1
            reserveCapacityIndices(minimumCapacity = newSize)
        }
        indices[indexCount] = index
        indexCount += 1
    }

    fun add(index1: Int, index2: Int, index3: Int) {
        if ((indexCount + 2) >= indexCapacity) {
            val newSize = (indexCount + 2) + ((indexCount + 2) / 2) + 1
            reserveCapacityIndices(minimumCapacity = newSize)
        }
        indices[indexCount + 0] = index1
        indices[indexCount + 1] = index2
        indices[indexCount + 2] = index3
        indexCount += 3
    }

    fun addTriangleIndices(startingAt: Int): Int {
        if ((indexCount + 2) >= indexCapacity) {
            val newSize = (indexCount + 2) + ((indexCount + 2) / 2) + 1
            reserveCapacityIndices(minimumCapacity = newSize)
        }
        indices[indexCount + 0] = startingAt + 0
        indices[indexCount + 1] = startingAt + 1
        indices[indexCount + 2] = startingAt + 2
        indexCount += 3
        return startingAt + 3
    }

    fun addTriangleQuadIndices(startingAt: Int): Int {
        if ((indexCount + 5) >= indexCapacity) {
            val newSize = (indexCount + 5) + ((indexCount + 5) / 2) + 1
            reserveCapacityIndices(minimumCapacity = newSize)
        }
        indices[indexCount + 0] = startingAt + 0
        indices[indexCount + 1] = startingAt + 1
        indices[indexCount + 2] = startingAt + 2
        indices[indexCount + 3] = startingAt + 1
        indices[indexCount + 4] = startingAt + 3
        indices[indexCount + 5] = startingAt + 2
        indexCount += 6
        return startingAt + 4
    }

    fun addTriangleQuad(startingAt: Int, vertex1: NodeType, vertex2: NodeType, vertex3: NodeType, vertex4: NodeType): Int {
        add(vertex1, vertex2, vertex3, vertex4)
        return addTriangleQuadIndices(startingAt)
    }

    fun addTriangle(startingAt: Int, vertex1: NodeType, vertex2: NodeType, vertex3: NodeType): Int {
        add(vertex1, vertex2, vertex3)
        return addTriangleIndices(startingAt)
    }

    fun addTriangle(startingAt: Int): Int {
        if ((indexCount + 2) >= indexCapacity) {
            val newSize = (indexCount + 2) + ((indexCount + 2) / 2) + 1
            reserveCapacityIndices(minimumCapacity = newSize)
        }
        indices[indexCount + 0] = startingAt + 0
        indices[indexCount + 1] = startingAt + 1
        indices[indexCount + 2] = startingAt + 2
        indexCount += 3
        return startingAt + 3
    }

    fun reserveCapacityVertices(minimumCapacity: Int, placeholder: NodeType) {
        if (minimumCapacity > vertexCapacity) {
            while (vertices.size < minimumCapacity) {
                vertices.add(placeholder)
            }
            vertexCapacity = minimumCapacity
        }
    }

    fun reserveCapacityIndices(minimumCapacity: Int) {
        if (minimumCapacity > indexCapacity) {
            while (indices.size < minimumCapacity) {
                indices.add(0)
            }
            indexCapacity = minimumCapacity
        }
    }

    fun load(graphics: GraphicsLibrary?) {
        this.graphics = graphics
    }

    fun writeVertexBuffer() {

        graphics?.let { _graphics ->

            if (vertexCount <= 0) {
                vertexBufferLength = 0
                return
            }

            val bytesPerItem = vertices[0].size() * Float.SIZE_BYTES
            val length = bytesPerItem * vertexCount

            if (vertexBuffer != null) {
                if (length > vertexBufferLength) {
                    vertexBufferLength = bytesPerItem * vertexCapacity
                    vertexBuffer = _graphics.bufferFloatGenerate(vertexBufferLength)
                    _graphics.bufferFloatWrite(vertices, vertexBuffer, vertexCount)
                } else {
                    _graphics.bufferFloatWrite(vertices, vertexBuffer, vertexCount)
                }
            } else {
                vertexBufferLength = bytesPerItem * vertexCapacity
                vertexBuffer = _graphics.bufferFloatGenerate(vertexBufferLength)
                _graphics.bufferFloatWrite(vertices, vertexBuffer, vertexCount)
            }
        }

    }

}
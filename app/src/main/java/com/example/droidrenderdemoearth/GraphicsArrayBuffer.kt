package com.example.droidrenderdemoearth
import java.nio.FloatBuffer


// Statically allocated graphics buffer.
// The content can be replaced, but it cannot
// change size...

class GraphicsArrayBuffer<T : FloatBufferable> {

    var graphics: GraphicsLibrary? = null
    var vertexBuffer: FloatBuffer? = null
    var bufferIndex: Int = -1
    var size: Int = 0

    fun load(graphics: GraphicsLibrary?, array: Array<T>?) {
        this.graphics = graphics
        graphics?.let { _graphics ->
            // Handle the case when graphics is not null
            // You can access `graphics` safely inside this block

            array?.let { _array ->
                size = _graphics.floatBufferSize(_array) * Float.SIZE_BYTES
                vertexBuffer = _graphics.floatBufferGenerate(_array)
                bufferIndex = _graphics.bufferArrayGenerate(size)
                vertexBuffer?.let {
                    _graphics.bufferArrayWrite(bufferIndex, size, it)
                }
            }
        }
    }

    fun write(array: Array<T>?) {
        array?.let { _array ->
            graphics?.floatBufferWrite(_array, vertexBuffer)
            graphics?.bufferArrayWrite(bufferIndex, size, vertexBuffer)
        }
    }
}
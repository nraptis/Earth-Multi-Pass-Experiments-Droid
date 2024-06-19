package com.example.droidrenderdemoearth

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLES20
import java.lang.ref.WeakReference
import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer
import kotlin.random.Random

// This is re-usable code, wrapping paper for OpenGL
// and all depends on already having the OpenGL context...

//ShaderLibrary
//GraphicsActivity

class GraphicsLibrary(activity: GraphicsActivity?,
                      renderer: GraphicsRenderer?,
                      pipeline: GraphicsPipeline?,
                      surfaceView: GraphicsSurfaceView?,
                      width: Int,
                      height: Int) {

    var width: Int
    var height: Int
    var widthf: Float
    var heightf: Float



    private val activityRef: WeakReference<GraphicsActivity> = WeakReference(activity)
    val activity: Context?
        get() = activityRef.get()

    private val rendererRef: WeakReference<GraphicsRenderer> = WeakReference(renderer)
    val renderer: GraphicsRenderer?
        get() = rendererRef.get()

    private val pipelineRef: WeakReference<GraphicsPipeline> = WeakReference(pipeline)
    val pipeline: GraphicsPipeline?
        get() = pipelineRef.get()

    private val surfaceViewRef: WeakReference<GraphicsSurfaceView> = WeakReference(surfaceView)
    val surfaceView: GraphicsSurfaceView?
        get() = surfaceViewRef.get()



    init {

        this.width = width
        this.height = height
        this.widthf = width.toFloat()
        this.heightf = height.toFloat()

        textureSetFilterLinear()
        textureSetClamp()
    }

    fun bufferArrayGenerate(length: Int): Int {
        if (length > 0) {
            val bufferHandle = IntArray(1)
            GLES20.glGenBuffers(1, bufferHandle, 0)
            return bufferHandle[0]
        }
        return -1
    }

    fun bufferArrayDelete(index: Int) {
        val bufferHandle = intArrayOf(index)
        GLES20.glDeleteBuffers(1, bufferHandle, 0)
    }

    fun bufferArrayWrite(index: Int, size: Int, buffer: Buffer?) {
        if (index != -1) {
            buffer.let { _buffer ->
                GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, index)
                GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, size, _buffer, GLES20.GL_STATIC_DRAW)
            }
        }
    }

    fun <T>bufferArrayBind(graphicsArrayBuffer: GraphicsArrayBuffer<T>?) where T : FloatBufferable {
        graphicsArrayBuffer?.let { _graphicsArrayBuffer ->
            if (_graphicsArrayBuffer.bufferIndex != -1) {
                GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, _graphicsArrayBuffer.bufferIndex)
            }
        }
    }

    fun bufferArrayBind(index: Int) {
        if (index != -1) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, index)
        }
    }

    fun bufferIndexGenerate(array: Array<Int>): IntBuffer {
        val result = ByteBuffer.allocateDirect(array.size * Int.SIZE_BYTES)
            .order(ByteOrder.nativeOrder())
            .asIntBuffer()
        bufferIndexWrite(array, result)
        return result
    }

    fun bufferIndexGenerate(list: List<Int>): IntBuffer {
        val result = ByteBuffer.allocateDirect(list.size * Int.SIZE_BYTES)
            .order(ByteOrder.nativeOrder())
            .asIntBuffer()
        bufferIndexWrite(list, result)
        return result
    }

    fun bufferIndexDelete(index: Int) {
        val bufferHandle = intArrayOf(index)
        GLES20.glDeleteBuffers(1, bufferHandle, 0)
    }

    fun bufferIndexWrite(array: Array<Int>, intBuffer: IntBuffer?) {

        // Reset buffer position to the beginning
        intBuffer?.let { _intBuffer ->
            _intBuffer.position(0)

            for (element in array) {
                _intBuffer.put(element)
            }

            // Reset buffer position to the beginning
            _intBuffer.position(0)
        }
    }

    fun bufferIndexWrite(list: List<Int>, intBuffer: IntBuffer?) {

        // Reset buffer position to the beginning
        intBuffer?.let { _intBuffer ->
            _intBuffer.position(0)

            for (element in list) {
                _intBuffer.put(element)
            }

            // Reset buffer position to the beginning
            _intBuffer.position(0)
        }
    }

    fun bufferIndexGenerate(array: IntArray): IntBuffer {
        val result = ByteBuffer.allocateDirect(array.size * Int.SIZE_BYTES)
            .order(ByteOrder.nativeOrder())
            .asIntBuffer()
        bufferIndexWrite(array, result)
        return result
    }

    fun bufferIndexWrite(array: IntArray, intBuffer: IntBuffer?) {
        // Reset buffer position to the beginning
        intBuffer?.let { _intBuffer ->
            _intBuffer.position(0)
            for (element in array) {
                _intBuffer.put(element)
            }
            _intBuffer.position(0)
        }
    }

    fun <T> floatBufferGenerate(item: T): FloatBuffer where T : FloatBufferable {

        val totalSize = item.size()
        val result = ByteBuffer.allocateDirect(totalSize * Float.SIZE_BYTES).run {
            order(ByteOrder.nativeOrder())
            asFloatBuffer()
        }
        floatBufferWrite(item, result)

        return result
    }

    fun <T> floatBufferGenerate(array: Array<T>): FloatBuffer where T : FloatBufferable {

        // Calculate the total size needed for the buffer
        val totalSize = floatBufferSize(array)

        // Allocate the buffer memory
        val result = ByteBuffer.allocateDirect(totalSize * Float.SIZE_BYTES).run {
            // Use native byte order
            order(ByteOrder.nativeOrder())
            asFloatBuffer()
        }
        floatBufferWrite(array, result)

        return result
    }

    fun <T> floatBufferGenerate(list: List<T>): FloatBuffer where T : FloatBufferable {

        // Calculate the total size needed for the buffer
        val totalSize = floatBufferSize(list)

        // Allocate the buffer memory
        val result = ByteBuffer.allocateDirect(totalSize * Float.SIZE_BYTES).run {
            // Use native byte order
            order(ByteOrder.nativeOrder())
            asFloatBuffer()
        }
        floatBufferWrite(list, result)

        return result
    }

    fun <T> floatBufferSize(array: Array<T>): Int where T : FloatBufferable {
        // Assume all elements in the array have the same size
        var elementSize = 0

        if (array.isNotEmpty()) {
            elementSize = array[0].size()
        }
        // Calculate the total size needed for the buffer
        return array.size * elementSize
    }

    fun <T> floatBufferSize(list: List<T>): Int where T : FloatBufferable {
        // Assume all elements in the array have the same size
        var elementSize = 0

        if (list.isNotEmpty()) {

            elementSize = list.get(0).size()
        }
        // Calculate the total size needed for the buffer
        return list.size * elementSize
    }

    fun <T> floatBufferWrite(array: Array<T>, floatBuffer: FloatBuffer?) where T : FloatBufferable {

        floatBuffer?.let { _floatBuffer ->
            // Reset buffer position to the beginning
            _floatBuffer.position(0)

            // Write each Bufferable's data to the buffer
            array.forEach { it.writeToBuffer(_floatBuffer) }

            // Reset buffer position to the beginning
            _floatBuffer.position(0)
        }
    }

    fun <T> floatBufferWrite(list: List<T>, floatBuffer: FloatBuffer?) where T : FloatBufferable {

        floatBuffer?.let { _floatBuffer ->
            // Reset buffer position to the beginning
            _floatBuffer.position(0)

            // Write each Bufferable's data to the buffer
            list.forEach {


                it.writeToBuffer(_floatBuffer)
            }

            // Reset buffer position to the beginning
            _floatBuffer.position(0)
        }
    }
    fun <T> floatBufferWrite(item: T, floatBuffer: FloatBuffer?) where T : FloatBufferable {

        floatBuffer?.let { _floatBuffer ->
            // Reset buffer position to the beginning
            _floatBuffer.position(0)

            item.writeToBuffer(_floatBuffer)

            // Reset buffer position to the beginning
            _floatBuffer.position(0)
        }
    }

    /*
    private var positionBuffer: FloatBuffer =
        // Allocate buffer memory
        ByteBuffer.allocateDirect(crumpsVertices.size * 6 * Float.SIZE_BYTES).run {
            // Use native byte order
            order(ByteOrder.nativeOrder())

            // Create FloatBuffer from ByteBuffer
            asFloatBuffer().apply {
                // Add coordinates to FloatBuffer
                crumpsVertices.forEach { vertex ->
                    put(vertex.x)
                    put(vertex.y)
                }
                // Reset buffer position to beginning
                position(0)
            }
        }

     */

    fun textureSetFilterMipMap() {
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MIN_FILTER,
            GLES20.GL_LINEAR_MIPMAP_LINEAR
        )
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MAG_FILTER,
            GLES20.GL_LINEAR_MIPMAP_LINEAR
        )
    }

    fun textureSetFilterLinear() {
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MIN_FILTER,
            GLES20.GL_LINEAR
        )
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_MAG_FILTER,
            GLES20.GL_LINEAR
        )

    }

    fun textureSetWrap() {
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_WRAP_S,
            GLES20.GL_REPEAT
        )
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_WRAP_T,
            GLES20.GL_REPEAT
        )
    }

    fun textureSetClamp() {
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_WRAP_S,
            GLES20.GL_CLAMP_TO_EDGE
        )
        GLES20.glTexParameteri(
            GLES20.GL_TEXTURE_2D,
            GLES20.GL_TEXTURE_WRAP_T,
            GLES20.GL_CLAMP_TO_EDGE
        )
    }

    fun textureBind(texture: GraphicsTexture?) {
        texture?.let { _texture ->
            if (_texture.textureIndex != -1) {
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _texture.textureIndex)
            }
        }
    }

    fun textureGenerate(bitmap: Bitmap?): Int {

        bitmap?.let {

            val pixels = IntArray(bitmap.width * bitmap.height)
            bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

            val textureHandle = IntArray(1)
            GLES20.glGenTextures(1, textureHandle, 0)

            if (textureHandle[0] != 0) {
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0])

                textureSetFilterLinear()
                textureSetClamp()

                // Convert ARGB to RGBA
                val buffer = ByteBuffer.allocateDirect(pixels.size * 4)
                buffer.order(ByteOrder.nativeOrder())
                for (color in pixels) {
                    val alpha = (color shr 24) and 0xFF
                    val red = (color shr 16) and 0xFF
                    val green = (color shr 8) and 0xFF
                    val blue = color and 0xFF
                    val rgba = ((alpha shl 24) and 0xFF000000.toInt()) or
                                ((green shl 8) and 0x0000FF00) or
                                ((blue shl 16) and 0x00FF0000) or
                                (red and 0x000000FF)
                    buffer.putInt(rgba)
                }
                buffer.position(0)

                GLES20.glTexImage2D(
                    GLES20.GL_TEXTURE_2D,
                    0,
                    GLES20.GL_RGBA, bitmap.width, bitmap.height,
                    0,
                    GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buffer)
            }
            return textureHandle[0]
        }
        return -1
    }

    fun textureGenerate(width: Int, height: Int): Int {

        val textureHandle = IntArray(1)
        GLES20.glGenTextures(1, textureHandle, 0)

        if (textureHandle[0] != 0) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0])


            val pixels = IntArray(width * height) { 0 }

            textureSetFilterLinear()
            textureSetClamp()

            // Convert ARGB to RGBA
            val buffer = ByteBuffer.allocateDirect(pixels.size * 4)
            buffer.order(ByteOrder.nativeOrder())
            for (color in pixels) {
                val alpha = Random.nextInt(0, 256)
                val red = Random.nextInt(0, 256)
                val green = Random.nextInt(0, 256)
                val blue = Random.nextInt(0, 256)
                val rgba = ((alpha shl 24) and 0xFF000000.toInt()) or
                        ((green shl 8) and 0x0000FF00) or
                        ((blue shl 16) and 0x00FF0000) or
                        (red and 0x000000FF)
                buffer.putInt(rgba)
            }
            buffer.position(0)

            GLES20.glTexImage2D(
                GLES20.GL_TEXTURE_2D,
                0,
                GLES20.GL_RGBA, width, height,
                0,
                GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buffer
            )
        }
        return textureHandle[0]


    }

    fun blendSetAlpha() {
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)
    }

    fun blendSetAdditive() {
        GLES20.glEnable(GLES20.GL_BLEND)
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE)
    }

    fun blendSetDisabled() {
        GLES20.glDisable(GLES20.GL_BLEND)
    }


    fun drawTriangles(indexBuffer: IntBuffer, count: Int) {
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, count, GLES20.GL_UNSIGNED_INT, indexBuffer)
    }

    fun drawTriangleStrips(indexBuffer: IntBuffer?, count: Int) {
        indexBuffer?.let {
            GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, count, GLES20.GL_UNSIGNED_INT, it)
        }
    }



    fun <T> linkBufferToShaderProgram(program: ShaderProgram?, buffer: GraphicsArrayBuffer<T>?) where T: FloatBufferable {
        buffer?.let { _buffer ->
            linkBufferToShaderProgram(program, _buffer.bufferIndex)
            /*
            program?.let { _program ->

                if (_program.program == 0) {
                    return
                }

                if (_buffer.bufferIndex == -1) {
                    return
                }

                GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, _buffer.bufferIndex)

                GLES20.glUseProgram(_program.program)

                if (_program.attributeLocationPosition != -1) {
                    GLES20.glEnableVertexAttribArray(_program.attributeLocationPosition)

                    GLES20.glVertexAttribPointer(_program.attributeLocationPosition,
                        _program.attributeSizePosition,
                        GLES20.GL_FLOAT,
                        false,
                        _program.attributeStridePosition,
                        _program.attributeOffsetPosition)
                }

                if (_program.attributeLocationTextureCoordinates != -1) {
                    GLES20.glEnableVertexAttribArray(_program.attributeLocationTextureCoordinates)

                    GLES20.glVertexAttribPointer(_program.attributeLocationTextureCoordinates,
                        _program.attributeSizeTextureCoordinates,
                        GLES20.GL_FLOAT,
                        false,
                        _program.attributeStrideTextureCoordinates,
                        _program.attributeOffsetTextureCoordinates)
                }

            }

             */
        }
    }

    fun linkBufferToShaderProgram(program: ShaderProgram?, bufferIndex: Int) {

        program?.let { _program ->

            if (_program.program == 0) {
                return
            }

            if (bufferIndex == -1) {
                return
            }

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, bufferIndex)

            GLES20.glUseProgram(_program.program)

            if (_program.attributeLocationPosition != -1) {
                GLES20.glEnableVertexAttribArray(_program.attributeLocationPosition)

                GLES20.glVertexAttribPointer(
                    _program.attributeLocationPosition,
                    _program.attributeSizePosition,
                    GLES20.GL_FLOAT,
                    false,
                    _program.attributeStridePosition,
                    _program.attributeOffsetPosition
                )
            }

            if (_program.attributeLocationTextureCoordinates != -1) {
                GLES20.glEnableVertexAttribArray(_program.attributeLocationTextureCoordinates)

                GLES20.glVertexAttribPointer(
                    _program.attributeLocationTextureCoordinates,
                    _program.attributeSizeTextureCoordinates,
                    GLES20.GL_FLOAT,
                    false,
                    _program.attributeStrideTextureCoordinates,
                    _program.attributeOffsetTextureCoordinates
                )
            }

        }

    }

    fun unlinkBufferFromShaderProgram(program: ShaderProgram?) {

        program?.let { _program ->

            if (_program.program == 0) {
                return
            }


            if (_program.attributeLocationTextureCoordinates != -1) {
                GLES20.glDisableVertexAttribArray(program.attributeLocationTextureCoordinates)
            }

            if (_program.attributeLocationPosition != -1) {
                GLES20.glDisableVertexAttribArray(_program.attributeLocationPosition)
            }

        }
    }


    fun uniformsTextureSizeSet(program: ShaderProgram?, width: Float, height: Float) {
        program?.let { _program ->
            if (_program.uniformLocationTextureSize != -1) {
                print("uniform width = " + width + ", height = " + height)
                GLES20.glUniform2f(_program.uniformLocationTextureSize, width, height)
            }
        }
    }

    // @Precondition: linkBufferToShaderProgram has been called with program.
    fun uniformsModulateColorSet(program: ShaderProgram?, color: Color) {
        program?.let { _program ->
            if (_program.uniformLocationModulateColor != -1) {
                GLES20.glUniform4f(_program.uniformLocationModulateColor, color.red, color.green, color.blue, color.alpha)
            }
        }
    }

    fun uniformsModulateColorSet(program: ShaderProgram?, red: Float, green: Float, blue: Float, alpha: Float) {
        program?.let { _program ->
            if (_program.uniformLocationModulateColor != -1) {
                GLES20.glUniform4f(_program.uniformLocationModulateColor, red, green, blue, alpha)
            }
        }
    }

    // @Precondition: linkBufferToShaderProgram has been called with program.
    fun uniformsModulateColorSet(program: ShaderProgram?, buffer: FloatBuffer?) {
        program?.let { _program ->
            if (_program.uniformLocationModulateColor != -1) {
                buffer.let { _buffer ->
                    GLES20.glUniform4fv(_program.uniformLocationModulateColor, 1, _buffer)
                }
            }
        }
    }



    // @Precondition: linkBufferToShaderProgram has been called with program.
    fun uniformsProjectionMatrixSet(program: ShaderProgram?, buffer: FloatBuffer?) {
        program?.let { _program ->
            if (_program.uniformLocationProjectionMatrix != -1) {
                buffer.let { _buffer ->
                    GLES20.glUniformMatrix4fv(
                        program.uniformLocationProjectionMatrix,
                        1,
                        false,
                        _buffer
                    )
                }
            }
        }
    }

    // @Precondition: linkBufferToShaderProgram has been called with program.
    fun uniformsProjectionMatrixSet(program: ShaderProgram?, matrix: Matrix?) {
        program?.let { _program ->
            if (_program.uniformLocationProjectionMatrix != -1) {
                matrix?.let { _matrix ->
                    val array = floatArrayOf(
                        _matrix.m00, _matrix.m01, _matrix.m02, _matrix.m03,
                        _matrix.m10, _matrix.m11, _matrix.m12, _matrix.m13,
                        _matrix.m20, _matrix.m21, _matrix.m22, _matrix.m23,
                        _matrix.m30, _matrix.m31, _matrix.m32, _matrix.m33)
                    GLES20.glUniformMatrix4fv(
                        program.uniformLocationProjectionMatrix,
                        1,
                        false,
                        array,
                        0
                    )
                }
            }
        }
    }

    // @Precondition: linkBufferToShaderProgram has been called with program.
    fun uniformsModelViewMatrixSet(program: ShaderProgram?, buffer: FloatBuffer?) {
        program?.let { _program ->
            if (_program.uniformLocationModelViewMatrix != -1) {
                buffer.let { _buffer ->
                    GLES20.glUniformMatrix4fv(
                        program.uniformLocationModelViewMatrix,
                        1,
                        false,
                        _buffer
                    )
                }
            }
        }
    }

    // @Precondition: linkBufferToShaderProgram has been called with program.
    fun uniformsModelViewMatrixSet(program: ShaderProgram?, matrix: Matrix?) {
        program?.let { _program ->
            if (_program.uniformLocationModelViewMatrix != -1) {
                matrix?.let { _matrix ->
                    val array = floatArrayOf(
                        _matrix.m00, _matrix.m01, _matrix.m02, _matrix.m03,
                        _matrix.m10, _matrix.m11, _matrix.m12, _matrix.m13,
                        _matrix.m20, _matrix.m21, _matrix.m22, _matrix.m23,
                        _matrix.m30, _matrix.m31, _matrix.m32, _matrix.m33
                    )
                    GLES20.glUniformMatrix4fv(
                        program.uniformLocationModelViewMatrix,
                        1,
                        false,
                        array,
                        0
                    )
                }
            }
        }
    }


    // @Precondition: linkBufferToShaderProgram has been called with program.
    // @Precondition: the texture is expected to be used on GL_TEXTURE0...
    fun uniformsTextureSet(program: ShaderProgram?, texture: GraphicsTexture?) {
        program?.let { _program ->
            if (_program.uniformLocationTexture != -1) {
                texture?.let { _texture ->
                    if (_texture.textureIndex != -1) {
                        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
                        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _texture.textureIndex)
                        GLES20.glUniform1i(_program.uniformLocationTexture, 0)
                    }
                }
            }
        }
    }

    // @Precondition: linkBufferToShaderProgram has been called with program.
    // @Precondition: the texture is expected to be used on GL_TEXTURE0...
    fun uniformsTextureSet(program: ShaderProgram?, sprite: Sprite?) {
        sprite?.let { _sprite ->
            uniformsTextureSet(program, _sprite.texture)
        }
    }

    // @Precondition: linkBufferToShaderProgram has been called with program.
    // @Precondition: the texture is expected to be used on GL_TEXTURE0...
    fun uniformsTextureSet(program: ShaderProgram?, textureIndex: Int) {
        program?.let { _program ->
            if (_program.uniformLocationTexture != -1) {
                if (textureIndex != -1) {
                    GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
                    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIndex)
                    GLES20.glUniform1i(_program.uniformLocationTexture, 0)
                }
            }
        }
    }


    //graphics?.linkBufferToShaderProgram(graphicsPipeline?.programSprite2D, gabbo)

    /*


    graphics?.linkBufferToShaderProgram(gabbo, graphicsPipeline?.programSprite2D)


    //GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureSlot)
    graphics?.textureBind(textureSlot)

    val program = graphicsPipeline!!.programSprite2D

    GLES20.glUniform1i(program.uniformLocationTexture, 0)

    //



*/

}
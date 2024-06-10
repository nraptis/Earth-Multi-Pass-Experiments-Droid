package com.example.droidrenderdemoearth
import android.opengl.GLES20
import android.util.Log

open class ShaderProgram(name: String, val vertexShader: Int, val fragmentShader: Int) {
    var program: Int = 0

    var attributeLocationPosition = -1
    var attributeLocationTextureCoordinates = -1

    var uniformLocationTexture = -1
    var uniformLocationModulateColor = -1
    var uniformLocationProjectionMatrix = -1
    var uniformLocationModelViewMatrix = -1

    var uniformLocationTextureSize = -1


    var attributeStridePosition = -1
    var attributeSizePosition = -1
    var attributeOffsetPosition = -1

    var attributeStrideTextureCoordinates = -1
    var attributeSizeTextureCoordinates = -1
    var attributeOffsetTextureCoordinates = -1




    init {
        if ((vertexShader > 0) && (fragmentShader > 0)) {
            program = loadProgram(vertexShader, fragmentShader)
            println("==> Success! Created Shader Program [" + name + "], vertexShader: " + vertexShader + ", fragmentShader: " + fragmentShader + ", program = " + program)


            val compileStatus = IntArray(1)
            GLES20.glGetShaderiv(program, GLES20.GL_COMPILE_STATUS, compileStatus, 0)
            if (compileStatus[0] == 0) {
                val log = GLES20.glGetShaderInfoLog(program)
                Log.e("ShaderCompile", "Error compiling shader: $log")
                GLES20.glDeleteShader(program)
            }


        } else {
            println("==> Failed! Created Shader Program [" + name + "], vertexShader: " + vertexShader + ", fragmentShader: " + fragmentShader)
            program = 0
        }
    }

    private fun loadProgram(vertexShader: Int, fragmentShader: Int): Int {
        return GLES20.glCreateProgram().also { program ->
            GLES20.glAttachShader(program, vertexShader)
            GLES20.glAttachShader(program, fragmentShader)
            GLES20.glLinkProgram(program)
        }
    }

    fun getAttributeLocation(attributeName: String): Int {
        if (program != 0) {
            return GLES20.glGetAttribLocation(program, attributeName)
        }
        return -1
    }

    fun getUniformLocation(uniformName: String): Int {
        if (program != 0) {
            return GLES20.glGetUniformLocation(program, uniformName)
        }
        return -1
    }
}


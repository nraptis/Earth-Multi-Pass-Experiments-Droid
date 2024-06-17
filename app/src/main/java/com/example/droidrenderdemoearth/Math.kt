package com.example.droidrenderdemoearth

import kotlin.math.PI

class Math {

    companion object {

        var pi = PI.toFloat()
        var pi2 = PI.toFloat() * 2.0f
        var pi_2 = PI.toFloat() / 2.0f
        var pi_4 = PI.toFloat() / 4.0f

        // Static function
        fun rotateX(float3: Float3, radians: Float): Float3 {
            val rotationMatrix = Matrix()
            rotationMatrix.rotateX(radians)
            return rotationMatrix.processRotationOnly(float3)
        }

        fun rotateY(float3: Float3, radians: Float): Float3 {
            val rotationMatrix = Matrix()
            rotationMatrix.rotateY(radians)
            return rotationMatrix.processRotationOnly(float3)
        }

        fun rotateZ(float3: Float3, radians: Float): Float3 {
            val rotationMatrix = Matrix()
            rotationMatrix.rotateZ(radians)
            return rotationMatrix.processRotationOnly(float3)
        }
    }

}
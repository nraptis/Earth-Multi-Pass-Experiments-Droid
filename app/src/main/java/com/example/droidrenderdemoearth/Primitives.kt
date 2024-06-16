package com.example.droidrenderdemoearth
import java.nio.FloatBuffer

interface StereoscopicConforming {
    var shift: Float
}

interface ColorConforming {
    var r: Float
    var g: Float
    var b: Float
    var a: Float
}

interface PositionConforming2D {
    var x: Float
    var y: Float
}

interface PositionConforming3D : PositionConforming2D {
    var z: Float
}

interface NormalConforming {
    var normalX: Float
    var normalY: Float
    var normalZ: Float
}

interface TextureCoordinateConforming {
    var u: Float
    var v: Float
}

data class Shape2DVertex(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f
) : PositionConforming2D, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
    }

    override fun size(): Int {
        return 2
    }
}

data class Shape2DColoredVertex(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var r: Float = 1.0f,
    override var g: Float = 1.0f,
    override var b: Float = 1.0f,
    override var a: Float = 1.0f
) : PositionConforming2D, ColorConforming, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)

        buffer.put(r)
        buffer.put(g)
        buffer.put(b)
        buffer.put(a)
    }

    override fun size(): Int {
        return 6
    }
}

data class Sprite2DVertex(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var u: Float = 0.0f,
    override var v: Float = 0.0f
) : PositionConforming2D, TextureCoordinateConforming, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)

        buffer.put(u)
        buffer.put(v)
    }

    override fun size(): Int {
        return 4
    }
}

data class Sprite2DColoredVertex(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var u: Float = 0.0f,
    override var v: Float = 0.0f,
    override var r: Float = 1.0f,
    override var g: Float = 1.0f,
    override var b: Float = 1.0f,
    override var a: Float = 1.0f
) : PositionConforming2D, TextureCoordinateConforming, ColorConforming, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)

        buffer.put(u)
        buffer.put(v)

        buffer.put(r)
        buffer.put(g)
        buffer.put(b)
        buffer.put(a)
    }

    override fun size(): Int {
        return 8
    }

}

data class Sprite3DVertex(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var z: Float = 0.0f,
    override var u: Float = 0.0f,
    override var v: Float = 0.0f
) : PositionConforming3D, TextureCoordinateConforming, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(z)

        buffer.put(u)
        buffer.put(v)
    }

    override fun size(): Int {
        return 5
    }
}

data class Sprite3DVertexStereoscopic(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var z: Float = 0.0f,
    override var u: Float = 0.0f,
    override var v: Float = 0.0f,
    override var shift: Float = 1.0f
) : PositionConforming3D, TextureCoordinateConforming, StereoscopicConforming, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(z)

        buffer.put(u)
        buffer.put(v)

        buffer.put(shift)
    }

    override fun size(): Int {
        return 6
    }
}

data class Sprite3DVertexColoredStereoscopic(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var z: Float = 0.0f,
    override var u: Float = 0.0f,
    override var v: Float = 0.0f,
    override var r: Float = 1.0f,
    override var g: Float = 1.0f,
    override var b: Float = 1.0f,
    override var a: Float = 1.0f,
    override var shift: Float = 1.0f
) : PositionConforming3D, TextureCoordinateConforming, StereoscopicConforming, ColorConforming, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(z)

        buffer.put(u)
        buffer.put(v)

        buffer.put(r)
        buffer.put(g)
        buffer.put(b)
        buffer.put(a)

        buffer.put(shift)
    }

    override fun size(): Int {
        return 10
    }

}

data class Sprite3DColoredVertex(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var z: Float = 0.0f,
    override var u: Float = 0.0f,
    override var v: Float = 0.0f,
    override var r: Float = 1.0f,
    override var g: Float = 1.0f,
    override var b: Float = 1.0f,
    override var a: Float = 1.0f
) : PositionConforming3D, TextureCoordinateConforming, ColorConforming, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(z)

        buffer.put(u)
        buffer.put(v)

        buffer.put(r)
        buffer.put(g)
        buffer.put(b)
        buffer.put(a)
    }

    override fun size(): Int {
        return 9
    }
}

data class Shape3DVertex(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var z: Float = 0.0f
) : PositionConforming3D, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(z)
    }

    override fun size(): Int {
        return 3
    }

}

data class Shape3DLightedVertex(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var z: Float = 0.0f,
    override var normalX: Float = 0.0f,
    override var normalY: Float = -1.0f,
    override var normalZ: Float = 0.0f
) : PositionConforming3D, NormalConforming, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(z)

        buffer.put(normalX)
        buffer.put(normalY)
        buffer.put(normalZ)
    }

    override fun size(): Int {
        return 6
    }

}

data class Sprite3DLightedStereoscopicVertex(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var z: Float = 0.0f,
    override var u: Float = 0.0f,
    override var v: Float = 0.0f,
    override var normalX: Float = 0.0f,
    override var normalY: Float = -1.0f,
    override var normalZ: Float = 0.0f,
    override var shift: Float = 1.0f
) : PositionConforming3D, TextureCoordinateConforming, StereoscopicConforming, NormalConforming, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(z)

        buffer.put(u)
        buffer.put(v)

        buffer.put(normalX)
        buffer.put(normalY)
        buffer.put(normalZ)

        buffer.put(shift)
    }

    override fun size(): Int {
        return 9
    }

}

data class Shape3DLightedColoredVertex(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var z: Float = 0.0f,
    override var normalX: Float = 0.0f,
    override var normalY: Float = -1.0f,
    override var normalZ: Float = 0.0f,
    override var r: Float = 1.0f,
    override var g: Float = 1.0f,
    override var b: Float = 1.0f,
    override var a: Float = 1.0f
) : PositionConforming3D, NormalConforming, ColorConforming, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(z)

        buffer.put(normalX)
        buffer.put(normalY)
        buffer.put(normalZ)

        buffer.put(r)
        buffer.put(g)
        buffer.put(b)
        buffer.put(a)
    }

    override fun size(): Int {
        return 10
    }
}

data class Shape3DColoredVertex(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var z: Float = 0.0f,
    override var r: Float = 1.0f,
    override var g: Float = 1.0f,
    override var b: Float = 1.0f,
    override var a: Float = 1.0f
) : PositionConforming3D, ColorConforming, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(z)

        buffer.put(r)
        buffer.put(g)
        buffer.put(b)
        buffer.put(a)
    }

    override fun size(): Int {
        return 7
    }

}

data class Sprite3DLightedVertex(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var z: Float = 0.0f,
    override var u: Float = 0.0f,
    override var v: Float = 0.0f,
    override var normalX: Float = 0.0f,
    override var normalY: Float = -1.0f,
    override var normalZ: Float = 0.0f
) : PositionConforming3D, NormalConforming, TextureCoordinateConforming, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(z)

        buffer.put(u)
        buffer.put(v)

        buffer.put(normalX)
        buffer.put(normalY)
        buffer.put(normalZ)
    }

    override fun size(): Int {
        return 8
    }

}

data class Sprite3DLightedColoredVertex(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var z: Float = 0.0f,
    override var u: Float = 0.0f,
    override var v: Float = 0.0f,
    override var normalX: Float = 0.0f,
    override var normalY: Float = -1.0f,
    override var normalZ: Float = 0.0f,
    override var r: Float = 1.0f,
    override var g: Float = 1.0f,
    override var b: Float = 1.0f,
    override var a: Float = 1.0f
) : PositionConforming3D, TextureCoordinateConforming, NormalConforming, ColorConforming, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(z)

        buffer.put(u)
        buffer.put(v)

        buffer.put(normalX)
        buffer.put(normalY)
        buffer.put(normalZ)

        buffer.put(r)
        buffer.put(g)
        buffer.put(b)
        buffer.put(a)
    }

    override fun size(): Int {
        return 12
    }
}

data class Sprite3DLightedColoredStereoscopicVertex(
    override var x: Float = 0.0f,
    override var y: Float = 0.0f,
    override var z: Float = 0.0f,
    override var u: Float = 0.0f,
    override var v: Float = 0.0f,
    override var normalX: Float = 0.0f,
    override var normalY: Float = -1.0f,
    override var normalZ: Float = 0.0f,
    override var r: Float = 1.0f,
    override var g: Float = 1.0f,
    override var b: Float = 1.0f,
    override var a: Float = 1.0f,
    override var shift: Float = 1.0f
) : PositionConforming3D, TextureCoordinateConforming, NormalConforming, ColorConforming, StereoscopicConforming, FloatBufferable {
    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(z)

        buffer.put(u)
        buffer.put(v)

        buffer.put(normalX)
        buffer.put(normalY)
        buffer.put(normalZ)

        buffer.put(r)
        buffer.put(g)
        buffer.put(b)
        buffer.put(a)

        buffer.put(shift)
    }

    override fun size(): Int {
        return 13
    }

}

/*
interface Colorable {
    var red: Float
    var green: Float
    var blue: Float
    var alpha: Float
}

interface Positionable2D {
    var x: Float
    var y: Float
}
interface Positionable3D: Positionable2D {
    var z: Float
}

interface Texturable2D {
    var u: Float
    var v: Float
}
interface Texturable3D: Texturable2D {
    var w: Float
}

data class VertexShape2D(override var x: Float, override var y: Float) : FloatBufferable, Positionable2D {

    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
    }

    override fun size(): Int {
        return 2
    }
}

data class VertexShape3D(override var x: Float, override var y: Float, override var z: Float) : FloatBufferable, Positionable3D {

    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(z)
    }

    override fun size(): Int {
        return 3
    }
}

data class VertexSprite2D(override var x: Float, override var y: Float, override var u: Float, override var v: Float) : FloatBufferable, Positionable2D, Texturable2D {

    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(u)
        buffer.put(v)
    }

    override fun size(): Int {
        return 4
    }
}

data class VertexSprite3D(override var x: Float, override var y: Float, override var z: Float, override var u: Float, override var v: Float) : FloatBufferable, Positionable3D, Texturable2D {

    override fun writeToBuffer(buffer: FloatBuffer) {
        buffer.put(x)
        buffer.put(y)
        buffer.put(z)

        buffer.put(u)
        buffer.put(v)
    }

    override fun size(): Int {
        return 5
    }
}
 */
package com.example.droidrenderdemoearth


open class IndexedSpriteBuffer<NodeType>(
    uniformsVertex: UniformsVertex,
    uniformsFragment: UniformsFragment
) : IndexedBuffer<NodeType>(uniformsVertex, uniformsFragment)
        where NodeType : PositionConforming2D, NodeType : TextureCoordinateConforming, NodeType : FloatBufferable {

    var sprite: Sprite? = null

    fun load(graphics: GraphicsLibrary?, sprite: Sprite?) {
        load(graphics)
        this.sprite = sprite
    }

    override fun link(shaderProgram: ShaderProgram?) {
        // Implementation for linking shader program
        graphics?.uniformsTextureSet(shaderProgram, sprite)
    }

}

class IndexedSpriteBuffer2D: IndexedSpriteBuffer<Sprite2DVertex>(
    UniformsSpriteVertex(),
    UniformsSpriteFragment())

class IndexedSpriteBuffer3D: IndexedSpriteBuffer<Sprite3DVertex>(
    UniformsSpriteVertex(),
    UniformsSpriteFragment())
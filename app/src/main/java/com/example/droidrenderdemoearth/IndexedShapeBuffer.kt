package com.example.droidrenderdemoearth

open class IndexedShapeBuffer<NodeType>(
    uniformsVertex: UniformsVertex,
    uniformsFragment: UniformsFragment
) : IndexedBuffer<NodeType>(uniformsVertex, uniformsFragment)
        where NodeType : PositionConforming2D, NodeType : FloatBufferable {

}

class IndexedShapeBuffer2D: IndexedShapeBuffer<Shape2DVertex>(
    UniformsShapeVertex(),
    UniformsShapeFragment())

class IndexedShapeBuffer3D: IndexedShapeBuffer<Shape3DVertex>(
    UniformsShapeVertex(),
    UniformsShapeFragment())
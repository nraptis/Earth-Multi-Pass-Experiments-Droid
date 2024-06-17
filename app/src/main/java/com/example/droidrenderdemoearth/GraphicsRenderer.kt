package com.example.droidrenderdemoearth

import android.content.Context
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import java.lang.ref.WeakReference
import java.nio.IntBuffer
import kotlin.random.Random

interface GraphicsScene {

    var width: Int
    var height: Int

    var context: Context?
    var activity: GraphicsActivity?

    var graphicsPipeline: GraphicsPipeline?
    var graphics: GraphicsLibrary?


    // Expected graphicsPipeline and graphics to be set by now...
    fun initialize(config: EGLConfig)

    fun load()
    fun loadComplete()

    fun update(deltaTime: Float)

    //fun
    fun draw3DPrebloom(width: Int, height: Int)
    fun draw3DBloom(width: Int, height: Int)

    fun draw3DStereoscopicLeft(width: Int, height: Int)
    fun draw3DStereoscopicRight(width: Int, height: Int)

    fun draw3D(width: Int, height: Int)
    fun draw2D(width: Int, height: Int)

}

class GraphicsRenderer(var scene: EarthScene?,
                       var context: Context?,
                       activity: GraphicsActivity?,
                       surfaceView: GraphicsSurfaceView?,
                       var width: Int,
                       var height: Int) : GLSurfaceView.Renderer {

    private lateinit var graphicsPipeline: GraphicsPipeline
    private lateinit var graphics: GraphicsLibrary
    private val surfaceViewRef: WeakReference<GraphicsSurfaceView> = WeakReference(surfaceView)
    val surfaceView: GraphicsSurfaceView?
        get() = surfaceViewRef.get()

    private val activityRef: WeakReference<GraphicsActivity> = WeakReference(activity)
    val activity: GraphicsActivity?
        get() = activityRef.get()


    private lateinit var earth: Earth

    val starBackground = GraphicsTexture()
    val earthMap = GraphicsTexture()
    val testTexture = GraphicsTexture()

    private val blurScale = 4


    val renderTargetPreBloom = GraphicsRenderTarget(frameBufferSpriteInstance = GraphicsSprite2DInstance())

    val renderTargetBloom = GraphicsRenderTarget(frameBufferSpriteInstance = GraphicsSprite2DInstance())

    val renderTargetBlur1 = GraphicsRenderTarget(frameBufferSpriteInstance = GraphicsSpriteBlurInstance())
    val renderTargetBlur2 = GraphicsRenderTarget(frameBufferSpriteInstance = GraphicsSpriteBlurInstance())


    //var blurInstance1 = GraphicsSpriteBlurInstance()
    //var blurInstance2 = GraphicsSpriteBlurInstance()


    init {

    }

    /*





    val indices = intArrayOf(0, 1, 2, 3)
    lateinit var indexBuffer: IntBuffer
    lateinit var vertexBuffer: GraphicsArrayBuffer<VertexSprite2D>

    var projectionMatrix = Matrix()
    var modelViewMatrix = Matrix()

     */

    //var frameBufferTextureIndex = -1
    //var frameBufferIndex = -1
    //var renderBufferIndex = -1
    //val frameBufferTexture = GraphicsTexture()
    //var frameBufferSpriteInstance = GraphicsSprite2DInstance()


    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {


        graphicsPipeline = GraphicsPipeline(context ?: return)
        graphics = GraphicsLibrary(activity, this, graphicsPipeline, surfaceView, width, height)


        scene?.graphicsPipeline = graphicsPipeline
        scene?.graphics = graphics
        scene?.initialize(config)



        renderTargetPreBloom.load(graphics, graphicsPipeline, width, height)
        renderTargetBloom.load(graphics, graphicsPipeline, width, height)


        //renderTargetBlur1.load(graphics, graphicsPipeline, width / 4, height / 4)
        //        renderTargetBlur2.load(graphics, graphicsPipeline, width / 4, height / 4)
        //
        //renderTargetBlur1.load(graphics, graphicsPipeline, width, height)
        //renderTargetBlur2.load(graphics, graphicsPipeline, width, height)

        val blurWidth = width / blurScale
        val blurHeight = height / blurScale

        renderTargetBlur1.load(graphics, graphicsPipeline, blurWidth, blurHeight)
        renderTargetBlur2.load(graphics, graphicsPipeline, blurWidth, blurHeight)

        //blurInstance1.load(graphics, renderTargetBlur1.frameBufferTexture)
        //blurInstance2.load(graphics, renderTargetBlur2.frameBufferTexture)



        //renderTargetPreBloom.frameBufferSpriteInstance.setPositionFrame(0.0f, 0.0f, width / 2.0f, height.toFloat())
        //renderTargetBloom.frameBufferSpriteInstance.setPositionFrame(width / 2.0f, 0.0f, width / 2.0f, height.toFloat())



        //frameBufferSpriteInstance.setPositionFrame(0.0f, 0.0f, width.toFloat(), height.toFloat())

        earth = Earth(graphics, graphicsPipeline, earthMap)

        scene?.earth = earth
        scene?.load()
        scene?.loadComplete()


        starBackground.load(context, graphics, "galaxy.jpg")
        earthMap.load(context, graphics, "earth_texture.jpg")
        testTexture.load(context, graphics, "test.png")

    }

    private var previousFrameTimestamp: Long? = null


    var spin = 0.0f
    override fun onDrawFrame(unused: GL10) {

        val currentFrameTimestamp = System.currentTimeMillis()
        var deltaTime = 0.0f
        previousFrameTimestamp?.let { _previousFrameTimestamp ->
            deltaTime = (currentFrameTimestamp - _previousFrameTimestamp).toFloat() / 1000.0f
        }
        scene?.update(deltaTime)
        previousFrameTimestamp = currentFrameTimestamp

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, renderTargetPreBloom.frameBufferIndex)
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        scene?.draw3DPrebloom(width, height)

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, renderTargetBloom.frameBufferIndex)
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        scene?.draw3DBloom(width, height)

        generateBloom()


        //renderTargetBlur1


        //renderTargetBlur1.load(graphics, graphicsPipeline, width / 4, height / 4)
        //renderTargetBlur2.load(graphics, graphicsPipeline, width / 4, height / 4)




        //


        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
        renderTargetPreBloom.frameBufferSpriteInstance.setPositionQuad(0.0f, height.toFloat(), width.toFloat(), height.toFloat(), 0.0f, 0.0f, width.toFloat(), 0.0f)
        renderTargetPreBloom.render()



        graphics?.blendSetAdditive()
        renderTargetBlur2.frameBufferSpriteInstance.setPositionQuad(0.0f, height.toFloat(), width.toFloat(), height.toFloat(), 0.0f, 0.0f, width.toFloat(), 0.0f)

        //Frame(0.0f, 0.0f, width.toFloat(), height.toFloat())
        renderTargetBlur2.frameBufferSpriteInstance.projectionMatrix.ortho(width, height)
        renderTargetBlur2.frameBufferSpriteInstance.render(graphicsPipeline?.programBlurHorizontal)



        graphics?.blendSetDisabled()

        //if (Random.nextBoolean()) {
            scene?.draw3D(width, height)
        //}


        surfaceView?.requestRender()
    }

    // At the end of this, "renderTargetBlur2" will contain
    // the frame buffer with the final bloom tile.
    private fun generateBloom() {

        val blurWidth = width / blurScale
        val blurHeight = height / blurScale

        val x1 = 0.0f
        val y1 = (height - blurHeight).toFloat()
        val x2 = blurWidth.toFloat()
        val y2 = (height).toFloat()

        renderTargetBloom.frameBufferSpriteInstance.setPositionQuad(x1, y1, x2, y1, x1, y2, x2, y2)
        renderTargetBlur1.frameBufferSpriteInstance.setPositionQuad(x1, y1, x2, y1, x1, y2, x2, y2)
        renderTargetBlur2.frameBufferSpriteInstance.setPositionQuad(x1, y1, x2, y1, x1, y2, x2, y2)

        renderTargetBloom.frameBufferSpriteInstance.projectionMatrix.ortho((width).toFloat(), (height).toFloat())
        renderTargetBlur1.frameBufferSpriteInstance.projectionMatrix.ortho((width).toFloat(), (height).toFloat())
        renderTargetBlur2.frameBufferSpriteInstance.projectionMatrix.ortho((width).toFloat(), (height).toFloat())

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, renderTargetBlur1.frameBufferIndex)
        renderTargetBloom.frameBufferSpriteInstance.render(graphicsPipeline?.programBlurHorizontal)

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, renderTargetBlur2.frameBufferIndex)
        renderTargetBlur1.frameBufferSpriteInstance.render(graphicsPipeline?.programBlurHorizontal)

        activity?.let { _activity ->
            for (i in 1 until _activity.bloomPasses) {
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, renderTargetBlur1.frameBufferIndex)
                renderTargetBlur2.frameBufferSpriteInstance.render(graphicsPipeline?.programBlurHorizontal)

                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, renderTargetBlur2.frameBufferIndex)
                renderTargetBlur1.frameBufferSpriteInstance.render(graphicsPipeline?.programBlurVertical)
            }
        }
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }
}

package com.example.droidrenderdemoearth

import android.content.Context
import java.nio.IntBuffer
import javax.microedition.khronos.egl.EGLConfig

class EarthScene(
    override var width: Int,
    override var height: Int,
    override var context: Context?,
    override var activity: GraphicsActivity?) : GraphicsScene {

    override var graphicsPipeline: GraphicsPipeline? = null
    override var graphics: GraphicsLibrary? = null

    val earthMap = GraphicsTexture()
    val galaxyMap = GraphicsTexture()
    val testTexture = GraphicsTexture()

    var earth: Earth? = null

    var galaxyInstance = GraphicsSprite2DInstance()


    var blahInstane = GraphicsSprite2DInstance()
    var davidBlane = GraphicsSprite3DInstance()
    var tomRizzo = GraphicsSpriteBlurInstance()
    var geraldoHanjab = GraphicsSpriteBlurInstance()

    var blahInstane2 = GraphicsSprite2DInstance()
    var davidBlane2 = GraphicsSprite3DInstance()
    var tomRizzo2 = GraphicsSpriteBlurInstance()
    var geraldoHanjab2 = GraphicsSpriteBlurInstance()



    var testShape1 = GraphicsShape2DInstance()
    var testShape2 = GraphicsShape3DInstance()




    override fun initialize(config: EGLConfig) {
        println("EarthScene => initialize")


    }

    override fun load() {
        println("EarthScene => load")


        earthMap.load(context, graphics, "earth_texture.jpg")
        testTexture.load(context, graphics, "test.png")


        galaxyMap.load(context, graphics, "colorful_galaxy_01.jpg")
        galaxyInstance.load(graphics, galaxyMap)
        galaxyInstance.projectionMatrix.ortho(width, height)
        galaxyInstance.setPositionQuad(0.0f, 0.0f,
            width.toFloat(), 0.0f,
            0.0f, height.toFloat(),
            width.toFloat(), height.toFloat())



        blahInstane.load(graphics, earthMap)
        blahInstane.projectionMatrix.ortho(width, height)

        davidBlane.load(graphics, earthMap)
        davidBlane.projectionMatrix.ortho(width, height)

        tomRizzo.load(graphics, testTexture)
        tomRizzo.projectionMatrix.ortho(width, height)

        geraldoHanjab.load(graphics, testTexture)
        geraldoHanjab.projectionMatrix.ortho(width, height)




        blahInstane2.load(graphics, earthMap)
        blahInstane2.projectionMatrix.ortho(width, height)

        davidBlane2.load(graphics, earthMap)
        davidBlane2.projectionMatrix.ortho(width, height)

        tomRizzo2.load(graphics, testTexture)
        tomRizzo2.projectionMatrix.ortho(width, height)

        geraldoHanjab2.load(graphics, testTexture)
        geraldoHanjab2.projectionMatrix.ortho(width, height)



        testShape1.load(graphics)
        testShape1.projectionMatrix.ortho(width, height)
        testShape1.setPositionFrame(100.0f, 100.0f, 100.0f, 300.0f)


        testShape2.load(graphics)
        testShape2.projectionMatrix.ortho(width, height)
        testShape2.setPositionFrame(300.0f, 400.0f, 100.0f, 600.0f)

    }

    override fun loadComplete() {
        println("EarthScene => loadComplete")

    }

    override fun update(deltaTime: Float) {
        //println("EarthScene => update ( " + deltaTime + " )")

    }

    //fun
    override fun draw3DPrebloom(width: Int, height: Int) {
        galaxyInstance.render(graphicsPipeline?.programSprite2D)
    }
    override fun draw3DBloom(width: Int, height: Int) {

        earth?.let {
            it.draw3DBloom(width, height)
        }

    }

    override fun draw3DStereoscopicLeft(width: Int, height: Int) {

    }
    override fun draw3DStereoscopicRight(width: Int, height: Int) {

    }

    var svn = 0.0f

    override fun draw3D(width: Int, height: Int) {
        earth?.let {
            it.draw3D(width, height)
        }
    }
    override fun draw2D(width: Int, height: Int) {



        /*
        blahInstane2.setPositionFrame(-512.0f, -512.0f, 1024.0f, 1024.0f)
        blahInstane2.modelViewMatrix.translation(width / 4.0f, height * 3.0f / 4.0f + 100.0f, 0.0f)
        blahInstane2.modelViewMatrix.rotateZ(svn)
        blahInstane2.modelViewMatrix.scale(0.75f)

        blahInstane2.render(graphicsPipeline?.programSprite2D)

        davidBlane2.setPositionFrame(-512.0f, -512.0f, 1024.0f, 1024.0f)
        davidBlane2.modelViewMatrix.translation(width / 4.0f, height / 4.0f + 100.0f, 0.0f)
        davidBlane2.modelViewMatrix.rotateZ(-svn)
        davidBlane2.modelViewMatrix.scale(0.75f)

        davidBlane2.render(graphicsPipeline?.programSprite3D)


        tomRizzo2.setPositionFrame(-512.0f, -512.0f, 1024.0f, 1024.0f)
        tomRizzo2.modelViewMatrix.translation(width * 3.0f / 4.0f, height * 3.0f / 4.0f + 100.0f, 0.0f)
        tomRizzo2.modelViewMatrix.rotateZ(-svn)
        tomRizzo2.modelViewMatrix.scale(0.75f)
        tomRizzo2.render(graphicsPipeline?.programBlurHorizontal)


        geraldoHanjab2.setPositionFrame(-512.0f, -512.0f, 1024.0f, 1024.0f)
        geraldoHanjab2.modelViewMatrix.translation(width * 3.0f / 4.0f, height / 4.0f + 100.0f, 0.0f)
        geraldoHanjab2.modelViewMatrix.rotateZ(-svn)
        geraldoHanjab2.modelViewMatrix.scale(0.75f)
        geraldoHanjab2.render(graphicsPipeline?.programBlurVertical)

         */

    }

}
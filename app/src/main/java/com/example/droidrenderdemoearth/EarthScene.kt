package com.example.droidrenderdemoearth

import android.content.Context
import javax.microedition.khronos.egl.EGLConfig
import kotlin.random.Random

class EarthScene(
    override var width: Int,
    override var height: Int,
    override var context: Context?,
    override var activity: GraphicsActivity?) : GraphicsScene {

    override var graphicsPipeline: GraphicsPipeline? = null
    override var graphics: GraphicsLibrary? = null

    val earthMapTexture = GraphicsTexture()
    val galaxyMapTexture = GraphicsTexture()
    val lightMapTexture = GraphicsTexture()

    val earthMap = Sprite()
    val galaxyMap = Sprite()
    val lightMap = Sprite()



    val testTexture = GraphicsTexture()

    var earth: Earth? = null

    //var galaxyInstance = GraphicsSprite2DInstance()

    var galaxyInstance = IndexedSpriteInstance3D()



    override fun initialize(config: EGLConfig) {
        println("EarthScene => initialize")

        earth = Earth(context, graphics, graphicsPipeline)

    }

    override fun load() {
        println("EarthScene => load")

        earthMapTexture.load(context, graphics, "earth_texture.jpg")
        galaxyMapTexture.load(context, graphics, "colorful_galaxy_01.jpg")
        lightMapTexture.load(context, graphics, "lights_texture.jpg")

        earthMap.load(graphics, earthMapTexture)
        galaxyMap.load(graphics, galaxyMapTexture)
        lightMap.load(graphics, lightMapTexture)


        earth?.load(graphics, graphicsPipeline, earthMap, lightMap, width, height)

        /*
        galaxyInstance.load(graphics, galaxyMap)
        galaxyInstance.projectionMatrix.ortho(width, height)
        galaxyInstance.setPositionQuad(0.0f, 0.0f,
            width.toFloat(), 0.0f,
            0.0f, height.toFloat(),
            width.toFloat(), height.toFloat())
         */


        val projectionMatrix = Matrix()
        projectionMatrix.ortho(width, height)

        galaxyInstance.load(graphics, galaxyMap)
        galaxyInstance.projectionMatrix.ortho(width, height)
        galaxyInstance.setPositionQuad(0.0f, 0.0f,
            width.toFloat(), 0.0f,
            0.0f, height.toFloat(),
            width.toFloat(), height.toFloat())


    }

    override fun loadComplete() {
        println("EarthScene => loadComplete")

    }


    var earthRotation = Math.pi
    var lightRotation = Math.pi_4
    var ticksConsumed = 1

    override fun update(deltaTime: Float) {
        //println("EarthScene => update ( " + deltaTime + " )")

        if (Random.nextInt(32) == 0) {
            ticksConsumed += 1
        }

        earthRotation += deltaTime * 0.4f
        if (earthRotation >= Math.pi2) {
            earthRotation -= Math.pi2
        }

        lightRotation -= deltaTime * 0.2f
        if (lightRotation < 0.0f) {
            lightRotation += Math.pi2
        }

        earth?.let {
            it.updateStereo(earthRotation,
                GraphicsActivity.stereoSpreadBase,
                GraphicsActivity.stereoSpreadMax,
                ticksConsumed)
        }

    }



    //fun
    override fun draw3DPrebloom(width: Int, height: Int) {
        //galaxyInstance.render(graphicsPipeline?.programSprite2D)
        galaxyInstance.render(graphicsPipeline?.programSprite3D)
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
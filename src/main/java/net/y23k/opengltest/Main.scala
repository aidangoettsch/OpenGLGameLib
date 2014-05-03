package net.y23k.opengltest

import _root_.org.lwjgl.opengl.{DisplayMode, GL11, Display}
import _root_.java.awt.event.{WindowEvent, WindowAdapter, ComponentEvent, ComponentAdapter}
import _root_.java.awt.{Dimension, Canvas, BorderLayout, Frame}
import _root_.net.y23k.opengltest.shapes.Obj
import _root_.java.util.concurrent.atomic.AtomicReference
import _root_.java.io.File

/**
 * Created by yayes2 on 4/5/14.
 */

object Main {
  var closeRequested : Boolean = false
  var newCanvasSize : AtomicReference[Dimension] = new AtomicReference[Dimension]()
  var objs : Seq[Obj] = Seq()

  def main(args: Array[String]) = {
    Display.setDisplayMode(new DisplayMode(800, 600))
    Display.create()

    GL11.glMatrixMode(GL11.GL_PROJECTION)
    GL11.glLoadIdentity()
    GL11.glOrtho(0, 800, 0, 600, 1, -1)
    GL11.glMatrixMode(GL11.GL_MODELVIEW)

    while (!Display.isCloseRequested) {
      loop()
      Display.update()
    }

    Display.destroy()

    val frame : Frame =  new Frame()
    frame.setLayout(new BorderLayout())
    val canvas : Canvas = new Canvas()

    canvas.addComponentListener(
      new ComponentAdapter() {
        override def componentResized(e : ComponentEvent) = {
          newCanvasSize.set(canvas.getSize)
        }
      }
    )

    frame.addWindowFocusListener(
      new WindowAdapter() {
        override def windowGainedFocus(e : WindowEvent) {
          canvas.requestFocusInWindow()
        }
      }
    )

    frame.addWindowListener(
      new WindowAdapter() {
        override def windowClosing(e : WindowEvent) {
          closeRequested = true
        }
      }
    )

    frame.add(canvas, BorderLayout.CENTER)

    Display.setParent(canvas)

    frame.setPreferredSize(new Dimension(1920, 1080))
    frame.setMinimumSize(new Dimension(800, 600))
    frame.pack()
    frame.setVisible(true)
    Display.create()

    addObjects()

    while(!Display.isCloseRequested && !closeRequested) {
      val newDim : Dimension = newCanvasSize.getAndSet(null)

      if (newDim != null) {
        GL11.glViewport(0, 0, newDim.width, newDim.height)
      }

      loop()
      Display.update()
    }

    Display.destroy()
    frame.dispose()
    System.exit(0)
  }

  def loop() = {
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)
    for (obj : Obj <- objs) {
      obj.render()
    }
  }

  def addObjects() = {
    objs.+(new Obj(new File))
  }

  /*def v2dtov3d(v3d : org.lwjgl.util.vector.Vector3f) : org.lwjgl.util.vector.Vector2f = {
    val x : Float = v3d.getX
    val y : Float = v3d.getY
    val z : Float = v3d.getZ
    val screenCoords : FloatBuffer = BufferUtils.createFloatBuffer(4)
    val viewport : IntBuffer = BufferUtils.createIntBuffer(16)
    val modelView : FloatBuffer = BufferUtils.createFloatBuffer(16)
    val projection : FloatBuffer = BufferUtils.createFloatBuffer(16)
    GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelView)
    GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection)
    GL11.glGetInteger(GL11.GL_VIEWPORT, viewport)
    GLU.gluProject(x, y, z, modelView, projection, viewport, screenCoords)
    new org.lwjgl.util.vector.Vector2f(screenCoords.get(0), screenCoords.get(1))
  }*/
}
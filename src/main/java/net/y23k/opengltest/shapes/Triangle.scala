package net.y23k.opengltest.shapes

import org.lwjgl.opengl.GL11

/**
 * Created by yayes2 on 4/14/14.
 */
class Triangle(point1 : Point, point2 : Point, point3 : Point) {
  def render() {
    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT)
    GL11.glColor3f(0.5f,0.5f,1.0f)
    GL11.glBegin(GL11.GL_TRIANGLES)
    GL11.glVertex2f(point1.getX, point1.getY)
    GL11.glVertex2f(point2.getX, point2.getY)
    GL11.glVertex2f(point3.getX, point3.getY)
    GL11.glEnd()
  }
}

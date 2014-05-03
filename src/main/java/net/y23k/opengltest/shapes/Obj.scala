package net.y23k.opengltest.shapes

import _root_.java.io.File
import _root_.java.nio.file.{Paths, Files}
import _root_.java.nio.charset.StandardCharsets
import org.lwjgl.util.vector.Vector3f

/**
 * Created by yayes2 on 4/14/14.
 */
class Obj(file : File) {
  var triangles : Seq[Triangle] = List()

  val lines : Seq[String] =
    new collection.jcl.BufferWrapper[String]() {
      def underlying = Files.readAllLines(Paths.get(file.getAbsolutePath), StandardCharsets.UTF_8)
    }

  for (line : String <- lines) {
    if (line.charAt(1).equals('v')) {
      val cords : Seq[Vector3f] = Seq()
      cords.+new Vector3f(line.split()(1), line.split()(2), line.split()(3))
    }
  }

  def render() = {

  }
}
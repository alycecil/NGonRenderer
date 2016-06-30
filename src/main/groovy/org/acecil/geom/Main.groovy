package org.acecil.geom

import org.acecil.geom.factories.PointGridShapeBuilder
import org.acecil.geom.factories.ShapeBuilder;
import org.acecil.geom.renderers.Renderer
import org.acecil.geom.renderers.SimpleXTriangleRenderer
import org.acecil.geom.renderers.SimpleYTriangleRenderer;


def type = 'PNG'

def shapeBuilders = [new PointGridShapeBuilder()]
def renderers = [new SimpleXTriangleRenderer(), new SimpleYTriangleRenderer()]
shapeBuilders.each { ShapeBuilder sb->
	def s = sb.buildShape()

	renderers.each{ Renderer r->
		def out = new File('out', "out_${System.currentTimeMillis()}.${type}")

		println "drawing ${out.getAbsolutePath()}"

		r.draw(s, out, type)
	}
	println 'DONE'
}

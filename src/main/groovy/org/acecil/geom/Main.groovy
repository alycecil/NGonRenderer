package org.acecil.geom

import org.acecil.geom.factories.PointGridShapeBuilder
import org.acecil.geom.factories.ScatterPointShapeBuilder
import org.acecil.geom.factories.ShapeBuilder
import org.acecil.geom.renderers.CentralOriginDistanceTriangleRenderer
import org.acecil.geom.renderers.GreedyTriangleRenderer
import org.acecil.geom.renderers.RandomOriginDistanceTriangleRenderer
import org.acecil.geom.renderers.Renderer
import org.acecil.geom.renderers.SimpleXTriangleRenderer
import org.acecil.geom.renderers.SimpleYTriangleRenderer
import org.acecil.geom.renderers.ZeroOriginDistanceTriangleRenderer


def type = 'PNG'
def rootOut = new File('out')

archiveOut(rootOut)

def shapeBuilders = [
	new PointGridShapeBuilder(),
	new ScatterPointShapeBuilder(),
]

def renderers = [
	new SimpleXTriangleRenderer(), 
	new SimpleYTriangleRenderer(),
	new GreedyTriangleRenderer(),
	new CentralOriginDistanceTriangleRenderer(),
	new RandomOriginDistanceTriangleRenderer(),
	new ZeroOriginDistanceTriangleRenderer(),
]


shapeBuilders.each { ShapeBuilder sb->
	def s = sb.buildShape()

	renderers.each{ Renderer r->
		def out = new File(rootOut, "${sb.class.getSimpleName()}_${r.class.getSimpleName()}_${System.currentTimeMillis()}.${type}")

		println "drawing ${out.getAbsolutePath()}"

		r.draw(s, out, type)
	}
}
println 'DONE'

private archiveOut(File rootOut) {
	def archive = new File(rootOut, 'archive')
	archive.mkdirs()
	rootOut.listFiles().each {

		if(it.isFile()){
			it.renameTo(new File(archive, it.name))
		}
	}
}

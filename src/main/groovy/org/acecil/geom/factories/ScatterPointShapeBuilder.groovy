package org.acecil.geom.factories

import groovy.transform.Canonical

import org.acecil.geom.common.Coordinate
import org.acecil.geom.common.Shape
import org.acecil.geom.util.ColorUtils;

@Canonical
class ScatterPointShapeBuilder implements ShapeBuilder {
	int nPoints = 100;
	int maxX = 100;
	int maxY = 100;
	
	public Shape buildShape() {

		def	s = new Shape(points : []);

		(1..nPoints).each {
			def x = Math.abs(ColorUtils.rand.nextInt()%maxX)
			def y = Math.abs(ColorUtils.rand.nextInt()%maxY)
			
			s.points << new Coordinate(x,y)
		}

		s
	}
}

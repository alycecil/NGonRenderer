package org.acecil.geom.factories

import groovy.transform.Canonical

import org.acecil.geom.common.Coordinate
import org.acecil.geom.common.Shape

@Canonical
class PointGridShapeBuilder implements ShapeBuilder{
	int xWidth = 100, yWidth = 100, xStep = 10, yStep = 20
	public Shape buildShape() {
		def	s = new Shape(points : []);

		0.step xWidth, xStep, { x->
			0.step yWidth, yStep, {y->
				s.points << new Coordinate(x,y)
			}
		}
		
		s
	}
}

package org.acecil.geom.renderers

import groovy.transform.CompileStatic

import org.acecil.geom.common.Coordinate
import org.acecil.geom.common.Shape

class SimpleYTriangleRenderer extends AbstactTriangleRenderer{
	@CompileStatic
	List<Coordinate> orderPoints(Shape s) {
		List<Coordinate> list = []

		list.addAll(s.getPoints())

		list.sort(new ClosureComparator({ Coordinate me,  Coordinate other ->
			if (me.y < other.y) return -1;
			if (me.y > other.y) return 1;
			if (me.x < other.x) return -1;
			if (me.x > other.x) return 1;
			0
		}))

		return list
	}
}

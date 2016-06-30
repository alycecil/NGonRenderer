package org.acecil.geom.renderers

import groovy.transform.CompileStatic

import java.util.List

import org.acecil.geom.common.Coordinate
import org.acecil.geom.common.Shape

class CentralOriginDistanceTriangleRenderer extends AbstactTriangleRenderer{
	@CompileStatic
	List<Coordinate> orderPoints(Shape s) {
		List<Coordinate> list = []

		list.addAll(s.getPoints())
		
		def origin = calculateOrigin(list)

		list.sort(new ClosureComparator({ Coordinate me,  Coordinate other ->
			def dMe, dOther
			dMe = origin.distanceSquare(me)
			dOther = origin.distanceSquare(other)
			
			if (dMe < dOther) return -1;
			if (dMe > dOther) return 1;
			0
		}))

		return list
	}

	public Coordinate calculateOrigin(List list) {
		def min = Double.MAX_VALUE;
		def origin = list[0]
		list.each {
			Coordinate me->
			double sumDSq = 0d
			list.each {
				Coordinate other->

				sumDSq+=me.distanceSquare(other)
			}

			if(sumDSq<min){
				min = sumDSq
				origin = me
			}
		}
		return origin
	}
}

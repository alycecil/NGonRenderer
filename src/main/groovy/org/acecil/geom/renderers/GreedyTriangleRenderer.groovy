package org.acecil.geom.renderers

import groovy.transform.CompileStatic

import java.util.List

import org.acecil.geom.common.Coordinate
import org.acecil.geom.common.Shape

class GreedyTriangleRenderer extends AbstactTriangleRenderer{
	@CompileStatic
	List<Coordinate> orderPoints(Shape s) {
		throw new IllegalStateException('NotUsed')
	}
	
	@CompileStatic
	List<Shape> buildTriangles(Shape s) {
		List<Coordinate> list = []
		list.addAll(s.points)
		list.sort()
		

		if(list.size()<3){
			throw new IllegalStateException('Triangles need 3 points')
		}
		
		List<Shape> triangles = []
		
		(0..list.size()-3).each{
			index->
			
			Coordinate me = list.get(index)
			
			Map<Double,List<Coordinate>> distances = [:]
			
			(index+1..list.size()-1).each{
				otherIndex ->
				Coordinate other = list.get(otherIndex)
				
				if(distances[me.distanceSquare(other)]==null){
					distances[me.distanceSquare(other)]=[]
				}
				distances[me.distanceSquare(other)] << other
			}
			
			def distancesList = new ArrayList<Double>(distances.keySet())
			distancesList.sort()
			
			def points = [me]
			int i = 0;
			while(points.size()<3){
				points.addAll(distances[distancesList.get(i)])
				
				i++
			}
			
			triangles << new Shape(points: [points[0], points[1], points[2]] as List<Coordinate>)
		}
		
		return triangles
	}
}

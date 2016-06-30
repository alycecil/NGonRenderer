package org.acecil.geom.renderers

import groovy.transform.CompileStatic

import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage

import javax.imageio.ImageIO

import org.acecil.geom.common.Coordinate
import org.acecil.geom.common.Shape
import org.acecil.geom.util.ColorUtils

abstract class AbstactTriangleRenderer implements Renderer{
	boolean drawNumber = true
	boolean fill = true
	boolean darkOutline = true

	@CompileStatic
	BufferedImage buildImage(double xRange, double scaleX, double yRange, double scaleY) {
		BufferedImage off_Image =
				new BufferedImage(
				(xRange*scaleX  as int)+1,
				(yRange*scaleY  as int)+1,
				BufferedImage.TYPE_INT_ARGB)
		return off_Image
	}

	@CompileStatic
	List<Shape> buildTriangles(Shape s) {
		List<Coordinate> list = orderPoints(s);

		if(list.size()<3){
			throw new IllegalStateException('Triangles need 3 points')
		}

		List<Shape> triangles = []

		(2..(list.size()-1)).each{
			triangles << new Shape(points: [list[it-2], list[it-1], list[it]] as List<Coordinate>)
		}
		return triangles
	}

	public void draw(Shape s, File out, String formatName){
		List<Shape> triangles = buildTriangles(s)

		def (int minX, int minY, int maxX, int maxY) = getMinMax(s)

		def xRange = Math.abs(maxX-minX),
		yRange = Math.abs(maxY-minY)
		xRange=xRange==0?1:xRange;
		yRange=yRange==0?1:yRange;

		def scaleX = (xRange<800?800d/xRange:1d),
		scaleY = (yRange<1000?1000d/yRange:1d)

		BufferedImage off_Image = buildImage(xRange, scaleX, yRange, scaleY);

		Graphics2D g2 = off_Image.createGraphics();

		renderTriangles(triangles, minX, minY, scaleX, scaleY, g2)

		out.getParentFile().mkdirs()
		ImageIO.write(off_Image, formatName, out)
	}

	@CompileStatic
	def getMinMax(Shape s){
		def list = s.getPoints()
		if(list.isEmpty()){
			throw new IllegalStateException('Need at least one point')
		}
		def minX = list[0].x, minY= list[0].y, maxX= list[0].x, maxY= list[0].y
		list.each {
			if(minX > it.x){
				minX = it.x
			}
			if(minY > it.y){
				minY = it.y
			}
			if(maxX < it.x){
				maxX = it.x
			}
			if(maxY < it.y){
				maxY = it.y
			}
		}
		[minX, minY, maxX, maxY]
	}

	public boolean isDarkOutline() {
		return darkOutline;
	}

	public boolean isDrawNumber() {
		return drawNumber;
	}

	public boolean isFill() {
		return fill;
	}

	abstract List<Coordinate> orderPoints(Shape s);

	def renderTriangles(List<Shape> triangles, int minX, int minY, double scaleX, double scaleY, Graphics2D g2) {
		def colors = ColorUtils.generateVisuallyDistinctColors(triangles.size(), 0.8f, 0.3f)
		def numbers = []
		triangles.eachWithIndex { Shape it, def i ->
			def xs = new int[3], ys = new int[3]
			int avgX=0, avgY=0
			it.points.eachWithIndex { Coordinate p, def index->
				xs[index]=(p.x-minX)*scaleX
				ys[index]=(p.y-minY)*scaleY

				avgX+=xs[index]
				avgY+=ys[index]
			}

			g2.setColor(colors[i])
			if(isFill()){
				g2.fillPolygon(xs as int[], ys as int[], 3)

				if(isDarkOutline()){
					g2.setColor(colors[i].darker())
				}
				g2.drawPolygon(xs as int[], ys as int[], 3)
			}else{
				g2.drawPolygon(xs as int[], ys as int[], 3)
			}
			if(isDrawNumber()){
				numbers<<["$i", avgX/3-7, avgY/3]
			}
		}

		if(isDrawNumber()){
			g2.setColor(Color.WHITE)
			numbers.each {
				g2.drawString(it[0] as String, it[1] as int, it[2]as int)
			}
		}
	}

	public void setDarkOutline(boolean darkOutline) {
		this.darkOutline = darkOutline;
	}

	public void setDrawNumber(boolean drawNumber) {
		this.drawNumber = drawNumber;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}
}

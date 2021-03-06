package org.acecil.geom.renderers

import java.util.List;

import org.acecil.geom.common.Coordinate;
import org.acecil.geom.util.ColorUtils;

class RandomOriginDistanceTriangleRenderer extends
		CentralOriginDistanceTriangleRenderer {

	@Override
	public Coordinate calculateOrigin(List list) {
		list[ColorUtils.rand.nextInt()%list.size()]
	}

}

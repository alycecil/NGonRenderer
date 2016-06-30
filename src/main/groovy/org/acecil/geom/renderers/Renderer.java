package org.acecil.geom.renderers;

import java.io.File;

import org.acecil.geom.common.Shape;

public interface Renderer {
	public void draw(Shape s, File out, String formatName);
}

/* 
GeoGebra - Dynamic Mathematics for Everyone
http://www.geogebra.org

This file is part of GeoGebra.

This program is free software; you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by 
the Free Software Foundation.

 */

package org.geogebra.common.kernel.algos;

import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.geos.GeoPolygon;
import org.geogebra.common.kernel.kernelND.GeoElementND;

/**
 * Creates all angles of a polygon.
 */

public class AlgoAnglePolygon extends AlgoAnglePolygonND {

	public AlgoAnglePolygon(Construction cons, String[] labels,
							GeoPolygon poly) {
		this(cons, labels, poly, false);
	}

	/**
	 * @param cons
	 *            construction
	 * @param labels
	 *            angle labels
	 * @param poly
	 *            polygon
	 */
	public AlgoAnglePolygon(Construction cons, String[] labels,
			GeoPolygon poly, boolean internalAngle) {
		this(cons, poly, internalAngle);
		// if only one label (e.g. "A"), new labels will be A_1, A_2, ...
		setLabels(labels);

		update();
	}

	public AlgoAnglePolygon(Construction cons, GeoPolygon poly) {
		this(cons, poly, false);
	}

	/**
	 * @param cons
	 *            construction
	 * @param poly
	 *            polygon
	 * 
	 */
	public AlgoAnglePolygon(Construction cons, GeoPolygon poly, boolean internalAngle) {
		super(cons, poly, null, internalAngle);
	}

	/**
	 * 
	 * @param cons1
	 *            construction
	 * @return helper algo
	 */
	@Override
	protected AlgoAnglePointsND newAlgoAnglePoints(Construction cons1) {
		return new AlgoAnglePoints(cons1);
	}

	// ///////////////////////////////
	// TRICKS FOR XOY PLANE
	// ///////////////////////////////

	@Override
	protected int getInputLengthForXML() {
		if (internalAngle) {
			return super.getInputLengthForXML();
		}
		return getInputLengthForXMLMayNeedXOYPlane();
	}

	@Override
	protected int getInputLengthForCommandDescription() {
		if (internalAngle) {
			return super.getInputLengthForCommandDescription();
		}
		return getInputLengthForCommandDescriptionMayNeedXOYPlane();
	}

	@Override
	public GeoElementND getInput(int i) {
		if (internalAngle) {
			return super.getInput(i);
		}
		return getInputMaybeXOYPlane(i);
	}

}

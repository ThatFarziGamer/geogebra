package org.geogebra.arbase;

import org.geogebra.common.euclidian.EuclidianController;
import org.geogebra.common.geogebra3D.euclidian3D.EuclidianView3D;
import org.geogebra.common.kernel.Matrix.Coords;
import org.geogebra.common.main.App;
import org.geogebra.common.main.Feature;

abstract public class ARGestureManager{

    protected EuclidianView3D mView;
    protected float mScaleFactor = 1.0f;
    protected Coords mPos = new Coords(2);
    protected boolean isTouched = false;
    protected boolean mUpdateOriginIsWanted = false;
    protected float mAngle;
    protected boolean actionPointerLeftPreviously = false;

    public ARGestureManager(EuclidianView3D view) {
        mView = view;
    }

    public void onRotationStart() {
        mView.rememberOrigins();
    }

    protected void onRotation(double angle) {
        mView.setCoordSystemFromMouseMove((int) angle, 0, EuclidianController.MOVE_ROTATE_VIEW);
    }

    public float getScaleFactor() {
        return mScaleFactor;
    }

    public void copyXYPosition(Coords ret) {
        ret.setX(mPos.getX());
        ret.setY(mPos.getY());
    }

    public boolean getIsTouched() {
        return isTouched;
    }

    public boolean getUpdateOriginIsWanted() {
        return mUpdateOriginIsWanted;
    }

    public void setUpdateOriginIsWanted(boolean updateOriginIsWanted) {
        mUpdateOriginIsWanted = updateOriginIsWanted;
    }

    public float getDAngle() {
        return mAngle;
    }

    protected void onMove(int pointerCount, App app) {
        if (app.has(Feature.G3D_AR_REGULAR_TOOLS)) {
            isTouched = pointerCount > 1;
        } else {
            isTouched = true;
        }

        if (actionPointerLeftPreviously) {
            mUpdateOriginIsWanted = true;
            actionPointerLeftPreviously = false;
        }
    }
}

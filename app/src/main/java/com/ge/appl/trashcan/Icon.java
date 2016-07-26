package com.ge.appl.trashcan;

import android.graphics.PointF;

/**
 * Created by 502646032 on 2016-07-20.
 */
public class Icon {
    private PointF mOrigin;
    private PointF mCurrent;
    private int iconId;

    public Icon(PointF origin, int iconId){
        mOrigin = origin;
        mCurrent = origin;
        this.iconId = iconId;
    }

    public PointF getCurrent(){
        return mCurrent;
    }

    public void setCurrent(PointF current){
        mCurrent = current;
    }

    public PointF getOrigin(){
        return mOrigin;
    }

    public int getIconId() {
        return iconId;
    }
}

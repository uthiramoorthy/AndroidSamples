package com.sb.mapdirection.util;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public abstract class BalloonItemizedOverlay<Item extends OverlayItem> extends
		ItemizedOverlay<Item> {

	private int viewOffset;

	public BalloonItemizedOverlay(Drawable defaultMarker) {
		super(defaultMarker);
	}

	public void setBalloonBottomOffset(int pixels) {
		viewOffset = pixels;
	}

	public int getBalloonBottomOffset() {
		return viewOffset;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e, MapView mapView) {
		return false;
	}
	
	protected boolean onBalloonTap(int index) {
		return false;
	}

}

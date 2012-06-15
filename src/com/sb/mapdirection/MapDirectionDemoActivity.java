package com.sb.mapdirection;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.sb.mapdirection.provider.DBConstants;
import com.sb.mapdirection.util.MyItemizedOverlay;

public class MapDirectionDemoActivity extends MapActivity {

	private static final String TAG = MapDirectionDemoActivity.class
			.getSimpleName();

	private Projection projection;

	private MapView mapView;

	private List<Overlay> mapOverlays;

	private LocationManager lm;

	private MapController mMapController;

	private MyLocationListener ll;

	private Drawable myLocationPin;

	private String[][] myLocations = new String[][]{new String[]{"",""}};
	
	private MyItemizedOverlay myLocation;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Drawable mapPinDrawable = getResources().getDrawable(R.drawable.pin);
		myLocationPin = getResources().getDrawable(R.drawable.map_pin_blue);
		mapView = (MapView) findViewById(R.id.myMapView1);
		Cursor cursor = getContentResolver().query(DBConstants.URI_VENUE, null,
				null, null, null);
		mapOverlays = mapView.getOverlays();
		mMapController = mapView.getController();
		mMapController.setZoom(14);
		MyItemizedOverlay overlay = new MyItemizedOverlay(mapPinDrawable);
		

		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			String lat = cursor.getString(cursor
					.getColumnIndex(DBConstants.COLUMN_LAT));
			String lng = cursor.getString(cursor
					.getColumnIndex(DBConstants.COLUMN_LONG));
			String postalcode = cursor.getString(cursor
					.getColumnIndex(DBConstants.COLUMN_POSTAL_CODE));
			GeoPoint point = new GeoPoint(
					(int) (Double.parseDouble(lat) * 1E6),
					(int) (Double.parseDouble(lng) * 1E6));
			OverlayItem item = new MapOverlayItem(point, postalcode, null);
			// item.setMarker(mapPinDrawable);
			overlay.addOverlay(item);

		}
		Log.d(TAG, "Venue Size : " + cursor.getCount());
		cursor.deactivate();
		mapOverlays.add(overlay);
		projection = mapView.getProjection();
	}
	
	private void showMyLocation() {
		if(myLocation != null)
			mapOverlays.remove(myLocation);
		myLocation = new MyItemizedOverlay(myLocationPin);
		
		GeoPoint point = new GeoPoint(
				(int) (Double.parseDouble("51.540900326386556") * 1E6),
				(int) (Double.parseDouble("-0.05759239196777344") * 1E6));
		mMapController.animateTo(point);
		OverlayItem item = new MapOverlayItem(point, null, null);
		myLocation.addOverlay(item);
		mapOverlays.add(myLocation);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		ll = new MyLocationListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
	}

	@Override
	protected void onPause() {
		lm.removeUpdates(ll);
		super.onPause();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	class MapOverlayItem extends OverlayItem {

		public MapOverlayItem(GeoPoint point, String title, String snippet) {
			super(point, title, null);
		}

	}

	class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
//			GeoPoint myGeoPoint = new GeoPoint(
//					(int) (location.getLatitude() * 1000000),
//					(int) (location.getLongitude() * 1000000));
//
			Toast.makeText(
					getBaseContext(),
					"New location latitude [" + location.getLatitude()
							+ "] longitude [" + location.getLongitude()
							+ "]", Toast.LENGTH_SHORT).show();
//
//			mMapController.animateTo(myGeoPoint);
			showMyLocation();

		}

		@Override
		public void onProviderDisabled(String provider) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.d(TAG, "status: " + status);
		}

	}

}
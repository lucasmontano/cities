package com.lucasmontano.cities.view.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lucasmontano.cities.R;

public class MapDialog extends DialogFragment implements OnMapReadyCallback {

  public static final String ARG_NAME = "ARG_NAME";
  public static final String ARG_LAT = "ARG_LAT";
  public static final String ARG_LON = "ARG_LON";

  private TextView mName;
  private TextView mCoord;
  private View mLoading;
  private View mMap;

  private Double lat;
  private Double lon;

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    lat = getArguments().getDouble(ARG_LAT);
    lon = getArguments().getDouble(ARG_LON);

    mName.setText(getArguments().getString(ARG_NAME));
    mCoord.setText(getString(R.string.coord, lat.toString(), lon.toString()));

    loadMap();
  }

  @Override
  public void onResume() {
    super.onResume();
    WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
    if (params != null) {
      params.width = WindowManager.LayoutParams.MATCH_PARENT;
      params.height = WindowManager.LayoutParams.WRAP_CONTENT;
      getDialog().getWindow().setAttributes(params);
    }
  }

  /**
   * Load and place the map.
   */
  private void loadMap() {
    SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.frame_map);
    mapFragment.getMapAsync(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_map, container, false);

    mName = view.findViewById(R.id.name);
    mCoord = view.findViewById(R.id.coord);
    mLoading = view.findViewById(R.id.loading);
    mMap = view.findViewById(R.id.frame_map);

    view.findViewById(R.id.button_close).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });

    return view;
  }

  /**
   * Add Marker in the Map with city Lat/Lon.
   *
   * @param googleMap GoogleMap
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {

    mLoading.setVisibility(View.GONE);
    mMap.setVisibility(View.VISIBLE);

    LatLng latlng = new LatLng(lat, lon);
    googleMap.addMarker(new MarkerOptions().position(latlng).title(mName.getText().toString()));
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 8f));
  }
}

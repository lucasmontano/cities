package com.lucasmontano.cities.view.activities;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lucasmontano.cities.R;
import com.lucasmontano.cities.data.City;
import com.lucasmontano.cities.view.fragments.ListCityFragment;
import com.lucasmontano.cities.view.interfaces.ListCitiesListener;

public class MainActivity extends AppCompatActivity implements ListCitiesListener,
    OnMapReadyCallback {

  private ListCityFragment listCityFragment;
  private EditText mSearch;

  private SupportMapFragment mapFragment;
  private GoogleMap googleMap;
  private BottomSheetBehavior<View> bottomSheetBehavior;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initSearchView();
    initListCityFragment();
    initMap();
  }

  /**
   * Init and Place Map Fragment with BottomSheet Behavior.
   */
  private void initMap() {

    View mBottomSheetMap = findViewById(R.id.bottom_sheet);
    bottomSheetBehavior = BottomSheetBehavior.from(mBottomSheetMap);
    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    bottomSheetBehavior.setHideable(true);
    bottomSheetBehavior.setPeekHeight(60);

    FragmentManager fm = getSupportFragmentManager();
    mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.frame_map);
    if (mapFragment == null) {
      mapFragment = SupportMapFragment.newInstance();
      fm.beginTransaction().replace(R.id.frame_map, mapFragment).commit();
    }

    mapFragment.getMapAsync(this);
  }

  /**
   * Input search in the Toolbar.
   * If the bottom sheet with map is expanded, collapse.
   */
  private void initSearchView() {
    mSearch = findViewById(R.id.search);
    mSearch.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
          bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        if (listCityFragment != null && listCityFragment.isAdded()) {
          listCityFragment.search(s.toString());
        }
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
  }

  /**
   * Add List City.
   */
  private void initListCityFragment() {

    listCityFragment = ListCityFragment.newInstance();
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.content, listCityFragment)
        .commitAllowingStateLoss();
  }

  /**
   * Show the city in the Map.
   *
   * @param city City clicked.
   */
  @Override
  public void onCityClick(City city) {

    if (googleMap == null) return;

    LatLng latlng = new LatLng(city.coord.lat, city.coord.lon);
    googleMap.addMarker(new MarkerOptions().position(latlng).title(city.name));
    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 8f));
    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
  }

  /**
   * On Map Loaded.
   *
   * @param googleMap google map android v2
   */
  @Override
  public void onMapReady(GoogleMap googleMap) {
    this.googleMap = googleMap;
  }

  @Override
  public void onCititesLoaded() {
    mSearch.setEnabled(true);
  }
}

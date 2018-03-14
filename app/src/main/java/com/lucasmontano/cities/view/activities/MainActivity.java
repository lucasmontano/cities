package com.lucasmontano.cities.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lucasmontano.cities.R;
import com.lucasmontano.cities.data.City;
import com.lucasmontano.cities.view.fragments.ListCityFragment;
import com.lucasmontano.cities.view.interfaces.OnCityListClick;

public class MainActivity extends AppCompatActivity implements OnCityListClick {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.content, ListCityFragment.newInstance())
        .commitAllowingStateLoss();
  }

  /**
   * Show the city in the Map.
   *
   * @param city City clicked.
   */
  @Override
  public void onCityClick(City city) {

  }
}

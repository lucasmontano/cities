package com.lucasmontano.cities.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.lucasmontano.cities.R;
import com.lucasmontano.cities.data.City;
import com.lucasmontano.cities.view.fragments.ListCityFragment;
import com.lucasmontano.cities.view.interfaces.ListCitiesListener;

public class MainActivity extends AppCompatActivity implements ListCitiesListener {

  private ListCityFragment listCityFragment;
  private EditText mSearch;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initSearchView();
    initListCityFragment();
  }

  /**
   * Search input in Toolbar.
   */
  private void initSearchView() {
    mSearch = findViewById(R.id.search);
    mSearch.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
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

  }

  @Override
  public void onCititesLoaded() {
    mSearch.setEnabled(true);
  }
}

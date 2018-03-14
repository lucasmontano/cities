package com.lucasmontano.cities.view.interfaces;

import com.lucasmontano.cities.data.City;

public interface ListCitiesListener {

  /**
   * On city is clicked.
   *
   * @param city
   */
  void onCityClick(City city);

  /**
   * On cities laoded.
   */
  void onCititesLoaded();
}

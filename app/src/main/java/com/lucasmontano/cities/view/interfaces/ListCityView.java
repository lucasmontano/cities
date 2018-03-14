package com.lucasmontano.cities.view.interfaces;

import android.content.Context;

import com.lucasmontano.cities.data.City;

import java.util.List;

public interface ListCityView {

  /**
   * List of city to be displayed.
   *
   * @param cities List of {@see City}
   */
  void displayCitites(List<City> cities);

  /**
   * Warn view to show loading.
   */
  void showLoading();

  /**
   * Warn view to hide loading.
   */
  void hideLoading();

  /**
   * Provide access to View Context.
   *
   * @return Android Context
   */
  Context getContext();

}

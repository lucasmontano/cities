package com.lucasmontano.cities.presenter;

import android.os.AsyncTask;

import com.lucasmontano.cities.data.City;
import com.lucasmontano.cities.model.CitiesDataManager;
import com.lucasmontano.cities.view.interfaces.ListCityView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ListCitiesPresenter {

  CitiesDataManager manager = CitiesDataManager.getInstance();

  private ListCityView mView;
  private AsyncLoadCities asyncLoadCities;

  /**
   * Init the presenter will setup the view and load the cities.
   *
   * @param view the view calling Presenter.
   */
  public void init(ListCityView view) {
    mView = view;
    asyncLoadCities = new AsyncLoadCities();
    asyncLoadCities.init(manager, mView);
    asyncLoadCities.execute();
  }

  /**
   * Search for a prefix.
   *
   * @param prefix query prefix.
   */
  public void searchCitites(String prefix) {
    asyncLoadCities.execute(prefix);
  }

  /**
   * On View destroyed, cancel async task.
   */
  public void onDestroy() {
    if (asyncLoadCities != null)
      asyncLoadCities.cancel(true);
  }

  /**
   * AsyncTask interacting with DataManager.
   *
   * @TODO change to RxJava2
   */
  private static class AsyncLoadCities extends AsyncTask<String, Integer, List<City>> {

    private CitiesDataManager manager;
    private ListCityView mView;

    void init(CitiesDataManager manager, ListCityView mView) {
      this.manager = manager;
      this.mView = mView;
    }

    protected List<City> doInBackground(String... prefixs) {

      mView.showLoading();

      try {
        InputStream citiesJson = mView.getContext().getAssets().open("cities.json");
        manager.loadFromInputStream(citiesJson);
      } catch (IOException e) {
        e.printStackTrace();
      }

      manager.sort();

      return manager.getListCities();
    }

    protected void onPostExecute(List<City> cities) {
      mView.displayCitites(cities);
      mView.hideLoading();
    }
  }
}

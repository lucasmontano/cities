package com.lucasmontano.cities.presenter;

import android.os.AsyncTask;

import com.lucasmontano.cities.data.City;
import com.lucasmontano.cities.model.CitiesDataManager;
import com.lucasmontano.cities.view.interfaces.ListCityView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ListCitiesPresenter {

  private CitiesDataManager manager = CitiesDataManager.getInstance();
  private ListCityView mView;
  private AsyncLoadCities asyncLoadCities;

  /**
   * Init the presenter will setup the view and load the cities.
   *
   * @param view the view calling Presenter.
   */
  public void init(ListCityView view) {
    mView = view;
    searchCitites(null);
  }

  /**
   * Search for a prefix.
   *
   * @param prefix query prefix.
   */
  public void searchCitites(String prefix) {

    // Stop last task, if any.
    if (asyncLoadCities != null && ! asyncLoadCities.isCancelled()) asyncLoadCities.cancel(true);

    asyncLoadCities = new AsyncLoadCities();
    asyncLoadCities.init(manager, mView);
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
      mView.showLoading();
    }

    protected List<City> doInBackground(String... prefixs) {

      if (manager.getListCities().size() == 0) {
        try {
          InputStream citiesJson = mView.getContext().getAssets().open("cities.json");
          manager.loadFromInputStream(citiesJson);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      manager.sort();

      return (prefixs[0] != null) ? manager.search(prefixs[0]) : manager.getListCities();
    }

    protected void onPostExecute(List<City> cities) {
      mView.displayCitites(cities);
      mView.hideLoading();
    }
  }
}

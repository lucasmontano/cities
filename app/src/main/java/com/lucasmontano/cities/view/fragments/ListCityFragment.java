package com.lucasmontano.cities.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lucasmontano.cities.R;
import com.lucasmontano.cities.data.City;
import com.lucasmontano.cities.presenter.ListCitiesPresenter;
import com.lucasmontano.cities.view.adapters.CityRecyclerViewAdapter;
import com.lucasmontano.cities.view.interfaces.ListCitiesListener;
import com.lucasmontano.cities.view.interfaces.ListCityView;

import java.util.List;

public class ListCityFragment extends Fragment implements ListCityView {

  private ListCitiesListener mListener;
  private ListCitiesPresenter listCitiesPresenter;
  private CityRecyclerViewAdapter adapter;
  private RecyclerView recyclerView;
  private ProgressBar loading;

  public static ListCityFragment newInstance() {
    return new ListCityFragment();
  }

  /**
   * On activity created state, init the ListCitiesPresenter.
   *
   * @param savedInstanceState bundle
   */
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    listCitiesPresenter = new ListCitiesPresenter();
    listCitiesPresenter.init(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_city_list, container, false);

    recyclerView =  view.findViewById(R.id.list);
    loading = view.findViewById(R.id.loading);

    initRecyclerView(view);

    return view;
  }

  /**
   * Init RecyclerView
   *
   * @param view inflated view.
   */
  private void initRecyclerView(View view) {
    Context context = view.getContext();
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    adapter = new CityRecyclerViewAdapter(mListener);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof ListCitiesListener) {
      mListener = (ListCitiesListener) context;
    } else {
      throw new RuntimeException(context.toString() + " must implement ListCitiesListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * On Fragment destroyed, warn presenter.
   */
  @Override
  public void onDestroy() {
    super.onDestroy();
    listCitiesPresenter.onDestroy();
  }

  /**
   * Update dataSet in Adapter.
   *
   * @param cities List of {@see City}
   */
  @Override
  public void displayCitites(List<City> cities) {
    adapter.setCities(cities);
    adapter.notifyDataSetChanged();
    mListener.onCititesLoaded();
  }

  /**
   * Show Loading and hide list.
   */
  @Override
  public void showLoading() {
    loading.setVisibility(View.VISIBLE);
    recyclerView.setVisibility(View.INVISIBLE);
  }

  /**
   * Hide Loading and show list.
   */
  @Override
  public void hideLoading() {
    loading.setVisibility(View.GONE);
    recyclerView.setVisibility(View.VISIBLE);
  }

  /**
   * Search prefix and update list.
   *
   * @param prefix String
   */
  public void search(String prefix) {
    listCitiesPresenter.searchCitites(prefix);
  }
}

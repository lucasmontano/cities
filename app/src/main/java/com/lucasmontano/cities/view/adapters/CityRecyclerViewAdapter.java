package com.lucasmontano.cities.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lucasmontano.cities.R;
import com.lucasmontano.cities.data.City;
import com.lucasmontano.cities.view.interfaces.ListCitiesListener;

import java.util.List;

public class CityRecyclerViewAdapter extends RecyclerView.Adapter<CityRecyclerViewAdapter.CityViewHolder> {

  private List<City> mCities;
  private final ListCitiesListener mListener;

  public CityRecyclerViewAdapter(ListCitiesListener listener) {
    mListener = listener;
  }

  public void setCities(List<City> cities) {
    this.mCities = cities;
  }

  @Override
  public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
    return new CityViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final CityViewHolder holder, int position) {
    holder.mItem = mCities.get(position);

    StringBuilder name = new StringBuilder();
    name.append(holder.mItem.name).append(", ").append(holder.mItem.country);
    holder.mName.setText(name.toString());

    holder.mView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (null != mListener) {
          mListener.onCityClick(holder.mItem);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return (mCities == null) ? 0 : mCities.size();
  }

  /**
   * City ViewHolder
   */
  public class CityViewHolder extends RecyclerView.ViewHolder {

    final View mView;
    final TextView mName;

    City mItem;

    CityViewHolder(View view) {
      super(view);
      mView = view;
      mName = view.findViewById(R.id.name);
    }

    @Override
    public String toString() {
      return super.toString() + " '" + mName.getText() + "'";
    }
  }
}

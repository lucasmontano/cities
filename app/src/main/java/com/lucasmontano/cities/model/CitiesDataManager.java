package com.lucasmontano.cities.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lucasmontano.cities.data.City;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

final class CitiesDataManager {

  private static CitiesDataManager instance = new CitiesDataManager();
  private List<City> listCities = new ArrayList<>();

  private CitiesDataManager() {}

  void loadFromFile(File file) throws IOException {
    InputStream inputStream = new FileInputStream(file);
    Reader reader = new InputStreamReader(inputStream);

    Gson gson = new Gson();
    Type listType = new TypeToken<ArrayList<City>>(){}.getType();
    listCities = gson.fromJson(reader, listType);
  }

  static CitiesDataManager getInstance() {
    return instance;
  }

  List<City> getListCities() {
    return listCities;
  }
}

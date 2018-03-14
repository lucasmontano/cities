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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.gedge.radixtree.RadixTree;

/**
 * DataSet Manager.
 */
public final class CitiesDataManager {

  private static CitiesDataManager instance = new CitiesDataManager();
  private List<City> listCities = new ArrayList<>();
  private RadixTree<City> radixTreeCities = new RadixTree<>();

  private CitiesDataManager() {}

  public static CitiesDataManager getInstance() {
    return instance;
  }

  /**
   * Load cities from a JSON File.
   *
   * @param file JSON file with cities
   * @throws IOException Exception from FileInputStream
   */
  public void loadFromFile(File file) throws IOException {
    InputStream inputStream = new FileInputStream(file);
    loadFromInputStream(inputStream);
  }

  /**
   * Load cities from a JSON InputStream.
   *
   * @param inputStream InputStream of a JSON File.
   */
  public void loadFromInputStream(InputStream inputStream) {
    Reader reader = new InputStreamReader(inputStream);

    Gson gson = new Gson();
    Type listType = new TypeToken<ArrayList<City>>(){}.getType();
    listCities = gson.fromJson(reader, listType);

    for (City city: listCities) {
      radixTreeCities.put(city.name + ", " + city.country, city);
    }
  }

  /**
   * Return the list of cities
   *
   * @return List of @see City
   */
  public List<City> getListCities() {
    return listCities;
  }

  /**
   * Using List and Collections.sort() (MergeSort algorithm). Another way could be an Array and
   * sort using Arrays.sort(), in this case, we would have the QuickSort algorithm in action.
   * The MergeSort sometimes can be slower then QuickSort, but definitely is more stable,
   * not depending on looky, which is extremely important when handling a 200k items =D
   */
  public void sort() {

    Collections.sort(listCities, new Comparator<City>() {

      @Override
      public int compare(City a, City b) {

        String valA = a.name + a.country;
        String valB = b.name + b.country;

        return valA.compareTo(valB);
      }
    });
  }

  /**
   * Given the task of search a prefix multiple times in the same cities list,
   * I decided to go with Radix Tree Algorithm. We will have an O(n) where n is the number of characters.
   *
   * Using an JAVA Radix Tree implementation by {@see https://github.com/thegedge/radix-tree} thegedge.
   *
   * @param prefix cities that starts with this prefix
   */
  public List<City> search(String prefix) {
    return radixTreeCities.getValuesWithPrefix(prefix);
  }
}

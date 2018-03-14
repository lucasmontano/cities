package com.lucasmontano.cities.model;

import com.lucasmontano.cities.data.City;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.net.URL;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CitiesDataManagerTest {

  private CitiesDataManager manager;
  private File fileCities;

  @Before
  public void setUp() throws Exception {
    manager = CitiesDataManager.getInstance();
    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource("cities-min.json");
    fileCities = new File(resource.getPath());
  }

  @Test
  public void loadFromFile() throws Exception {
    manager.loadFromFile(fileCities);
    int sizeListCities = manager.getListCities().size();
    Assert.assertEquals("Expected 304 for this test but got " + sizeListCities,304, sizeListCities);
  }

  @Test
  public void getListCities() throws Exception {
    manager.loadFromFile(fileCities);
    Assert.assertNotNull("Expected a list of cities, but got nothing :(", manager.getListCities());
  }

  @Test
  public void sortCities() throws Exception {

    if (manager.getListCities().size() == 0) {
      manager.loadFromFile(fileCities);
    }

    manager.sort();

    City city = manager.getListCities().get(0);
    Assert.assertEquals("Expected the first city to be Alupka but was " + city.name, "Alupka", city.name);
  }

  @Test
  public void searchPrefixM() throws Exception {

    if (manager.getListCities().size() == 0) {
      manager.loadFromFile(fileCities);
    }

    List<City> cities = manager.search("M");

    Assert.assertEquals("Expected 27 cities starting with M but got " + cities.size(), 27, cities.size());
  }

  @Test
  public void searchPrefixMo() throws Exception {

    if (manager.getListCities().size() == 0) {
      manager.loadFromFile(fileCities);
    }

    List<City> cities = manager.search("Mo");

    Assert.assertEquals("Expected 9 cities starting with Mo but got " + cities.size(), 9, cities.size());
  }

  @Test
  public void searchPrefixNull() throws Exception {

    if (manager.getListCities().size() == 0) {
      manager.loadFromFile(fileCities);
    }

    List<City> cities = manager.search(null);

    Assert.assertEquals("Searching a null prefix, expected the full list of cities.", 304, cities.size());
  }

  @Test
  public void searchPrefixEmpty() throws Exception {

    if (manager.getListCities().size() == 0) {
      manager.loadFromFile(fileCities);
    }

    List<City> cities = manager.search("");

    Assert.assertEquals("Searching a empty prefix, expected the full list of cities.", 304, cities.size());
  }

}
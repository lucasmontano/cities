package com.lucasmontano.cities.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.net.URL;

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
}
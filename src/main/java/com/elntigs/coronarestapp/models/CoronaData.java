package com.elntigs.coronarestapp.models;

/** maps the details of each data record */
public class CoronaData implements CoronaDataMap{

  //data members to be retrieved
  private String state, country;
  float latitude, longitude;
  private int latestTotalReportedCases, 
              latestRecoveredCases, 
              diffFromPrevDay,
              totalRecoveredCases,
              newRecoveries;

  /** list of the functions retrieving the data from the csv library generated response */
  @Override
  public void setState(String state) {
    this.state = state;
  }

  @Override
  public String getState() {
    return state;
  }

  @Override
  public void setCountry(String country) {
    this.country = country;

  }

  @Override
  public String getCountry() {
    return country;
  }

  @Override
  public void setLatitude(float latitude) {
    this.latitude = latitude;

  }

  @Override
  public float getLatitude() {
    return latitude;
  }

  @Override
  public void setLongitude(float longitude) {
    this.longitude = longitude;

  }

  @Override
  public float getLongitude() {
    return longitude;
  }

  @Override
  public void setLatestTotalReportedCases(int latestReportedCases) {
    this.latestTotalReportedCases = latestReportedCases;

  }

  @Override
  public int getLatestTotalReportedCases() {
    return latestTotalReportedCases;
  }

  @Override
  public void setDiffFromPreviousDay(int difference) {
    this.diffFromPrevDay = difference;
  }

  @Override
  public int getDiffFromPreviousDay() {
    return diffFromPrevDay;
  }

  @Override
  public void setLatestRecoveredCases(int latestRecoveredCases) {
    this.latestRecoveredCases = latestRecoveredCases;
  }

  @Override
  public int getLatestRecoveredCases() {
    return latestRecoveredCases;
  }

  @Override
  public void setNewRecoveriesFromPrev(int newRecoveries) {
    this.newRecoveries = newRecoveries;
  }

  @Override
  public int getNewRecoveriesFromPrev() {
    return newRecoveries;
  }

  @Override
  public void setTotalRecoveredCases(int totalRecoveredCases) {
   this.totalRecoveredCases = totalRecoveredCases;

  }

  @Override
  public int getTotalRecoveredCases() {
   return totalRecoveredCases;
  }
  
}

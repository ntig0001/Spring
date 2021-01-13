package com.elntigs.coronarestapp.models;

public interface CoronaDataMap {
  
  void setState(String state);
  String getState();
  
  void setCountry(String country);
  String getCountry();

  void setLatitude(float latitude);
  float getLatitude();

  void setLongitude(float longitude);  
  float getLongitude();

  void setLatestTotalReportedCases(int latestReportedCases);
  int getLatestTotalReportedCases();  

  void setDiffFromPreviousDay(int difference);
  int getDiffFromPreviousDay();

  void setLatestRecoveredCases(int latestRecoveredCases);
  int getLatestRecoveredCases();

  void setNewRecoveriesFromPrev(int newRecoveries);
  int getNewRecoveriesFromPrev();

  void setTotalRecoveredCases(int totalRecoveredCases);
  int getTotalRecoveredCases();

}

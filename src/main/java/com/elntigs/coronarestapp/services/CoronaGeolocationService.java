package com.elntigs.coronarestapp.services;

import org.springframework.stereotype.Service;

@Service
public class CoronaGeolocationService {
  
  private float latitude;
  private float longitude;

  public CoronaGeolocationService(){
  }
  
  public CoronaGeolocationService(float latitude, float longitude){
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public float getDataLatitude() {
    return latitude;
  }

  public void setDataLatitude(float latitude) {
    this.latitude = latitude;
  }

  public float getDataLongitude() {
    return longitude;
  }

  public void setDataLongitude(float longitude) {
    this.longitude = longitude;
  }
  
}

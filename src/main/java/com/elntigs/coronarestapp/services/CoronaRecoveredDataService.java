package com.elntigs.coronarestapp.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.elntigs.coronarestapp.models.CoronaData;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CoronaRecoveredDataService {
  
  // data source URL to be processed
  private static String CORONA_RECOVERED_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";

  // holds all data needed by their fields from the source URL
  private List<CoronaData> allRecoveredRecords = new ArrayList<>();
  private float latitude, longitude; //planned for empty data

  public List<CoronaData> getAllStats() {
    return allRecoveredRecords;
  }

  /**
   * process the data pulled from the source URL using CSV library
   * 
   * @throws InterruptedException
   * @throws IOException
   */
  @PostConstruct
  @Scheduled(cron = "* * 1 * * *")
  public void fetchReportedData() throws IOException, InterruptedException {
    List<CoronaData> newRecoveredRecords = new ArrayList<>();
    //setup http request and response
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
                          .uri(URI.create(CORONA_RECOVERED_DATA_URL))
                          .build();
    HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
    StringReader csvBodyBuilder = new StringReader(httpResponse.body());

    //handle acquired data
    Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyBuilder);
    for(CSVRecord record: records){
      CoronaData recoveredDataRecord = new CoronaData();
      recoveredDataRecord.setState(record.get("Province/State"));
      recoveredDataRecord.setCountry(record.get("Country/Region"));
      
      //catch emty records for latitude and longitude
      String latRecord = record.get("Lat");
      String longRecord = record.get("Long");
      if(!latRecord.isEmpty() || !longRecord.isEmpty()){
        latitude = Float.parseFloat(latRecord);
        longitude = Float.parseFloat(longRecord);
      } else {
        latitude = longitude = 0;
      }
      recoveredDataRecord.setLatitude(latitude);
      recoveredDataRecord.setLongitude(longitude);
      int latestRecoveredCases = Integer.parseInt(record.get(record.size() - 1));
      int recoveredPrevCases = Integer.parseInt(record.get(record.size() - 2));
      recoveredDataRecord.setLatestRecoveredCases(latestRecoveredCases);
      recoveredDataRecord.setNewRecoveriesFromPrev(latestRecoveredCases - recoveredPrevCases);
      newRecoveredRecords.add(recoveredDataRecord);
    }
    this.allRecoveredRecords = newRecoveredRecords;
  }

}

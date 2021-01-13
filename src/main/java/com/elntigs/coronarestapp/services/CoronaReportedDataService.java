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
public class CoronaReportedDataService {

  // data source URL to be processed
  private static String CORONA_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";


  // holds all data needed by their fields from the source URL
  private List<CoronaData> allReportedRecords = new ArrayList<>();
  private float latitude, longitude; //planned for empty data

  public List<CoronaData> getAllStats() {
    return allReportedRecords;
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
    List<CoronaData> newReportedRecords = new ArrayList<>();
    //setup http request and response
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
                          .uri(URI.create(CORONA_DATA_URL))
                          .build();
    HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
    StringReader csvBodyBuilder = new StringReader(httpResponse.body());

    //handle acquired data
    Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyBuilder);
    for(CSVRecord record: records){
      CoronaData reportedDataRecord = new CoronaData();
      reportedDataRecord.setState(record.get("Province/State"));
      reportedDataRecord.setCountry(record.get("Country/Region"));

      //catch emty records for latitude and longitude
      String latRecord = record.get("Lat");
      String longRecord = record.get("Long");
      if(!latRecord.isEmpty() || !longRecord.isEmpty()){
        latitude = Float.parseFloat(latRecord);
        longitude = Float.parseFloat(longRecord);
      } else {
        latitude = longitude = 0;
      }
      reportedDataRecord.setLatitude(latitude);
      reportedDataRecord.setLongitude(longitude);

      int latestReportedCases = Integer.parseInt(record.get(record.size() - 1));
      int reportedPrevCases = Integer.parseInt(record.get(record.size() - 2));
      reportedDataRecord.setLatestTotalReportedCases(latestReportedCases);
      reportedDataRecord.setDiffFromPreviousDay(latestReportedCases - reportedPrevCases);
      newReportedRecords.add(reportedDataRecord);
    }
    this.allReportedRecords = newReportedRecords;
  }

}

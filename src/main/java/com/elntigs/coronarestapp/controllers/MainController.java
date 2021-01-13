package com.elntigs.coronarestapp.controllers;

import java.util.List;

import com.elntigs.coronarestapp.models.CoronaData;
import com.elntigs.coronarestapp.services.CoronaGeolocationService;
import com.elntigs.coronarestapp.services.CoronaRecoveredDataService;
import com.elntigs.coronarestapp.services.CoronaReportedDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/** handles all the services of the app */
@Controller
public class MainController {

  @Autowired
  private CoronaReportedDataService coronaReportedDataService;

  @Autowired  
  private CoronaRecoveredDataService coronaRecoveredDataService;

  /**takes user to home page */
  @RequestMapping("/")
  public String home(Model model){
    List<CoronaData> allReportedRecords = coronaReportedDataService.getAllStats(); 
    int totalReportedCases = allReportedRecords.stream().mapToInt(records -> records.getLatestTotalReportedCases()).sum();
    int totalNewCases = allReportedRecords.stream().mapToInt(records -> records.getDiffFromPreviousDay()).sum();
    model.addAttribute("reportedRecords", allReportedRecords);
    model.addAttribute("totalReportedCases", totalReportedCases);
    model.addAttribute("totalNewCases", totalNewCases);
    return "index";
  }

  /** takes user to the recovery page */
  @RequestMapping("/recovered")
  public String displayRecovered(Model model){
    List<CoronaData> allRecoveredRecords = coronaRecoveredDataService.getAllStats(); 
    int totalRecoveredCases = allRecoveredRecords.stream().mapToInt(records -> records.getLatestRecoveredCases()).sum();
    int totalNewRecoveredCases = allRecoveredRecords.stream().mapToInt(records -> records.getNewRecoveriesFromPrev()).sum();
    model.addAttribute("recoveredRecords", allRecoveredRecords);
    model.addAttribute("totalRecoveredCases", totalRecoveredCases);
    model.addAttribute("totalNewRecoveredCases", totalNewRecoveredCases);
    return "recovered";
  }

  /** redirects user to home page from their current page */
  @RequestMapping("/home")
  public String returnHome(){
    return "redirect:/";
  }

  /**takes user to the location page by acquiring latitude and longitude passed in on the home page and the recovery page */
  @RequestMapping("/geolocation/{latitude}/{longitude}")
  public String findLocation(Model model, @PathVariable float latitude, @PathVariable float longitude){
    CoronaGeolocationService geolocation = new CoronaGeolocationService(latitude, longitude);
    model.addAttribute("latitude", geolocation.getDataLatitude());
    model.addAttribute("longitude", geolocation.getDataLongitude());
    return "geolocation";
  }

}

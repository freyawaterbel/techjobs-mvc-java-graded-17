package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.launchcode.techjobsmvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.
    @PostMapping(value="results")
    public String displaySearchResults(Model model, @RequestParam String searchTerm, @RequestParam String searchType) {
        model.addAttribute("columns", columnChoices);
//        model.addAttribute("defaultColumn", searchType);
        ArrayList<Job> jobs;
        if (searchTerm.equals("all") || searchTerm.isEmpty()) {
            jobs = JobData.findAll();
        } else if (searchType.equals("all")) {
            jobs = JobData.findByValue(searchTerm);
            model.addAttribute("title", "All Jobs with : " + searchTerm);
        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("title", "Jobs in " + columnChoices.get(searchType) + " with: " + searchTerm);
        }
        model.addAttribute("jobs", jobs);
        return "search";
    }

}


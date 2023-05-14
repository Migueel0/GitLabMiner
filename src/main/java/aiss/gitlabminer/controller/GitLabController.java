package aiss.gitlabminer.controller;

import aiss.gitlabminer.model.Project;
import aiss.gitlabminer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/gitlabminer")
public class GitLabController {

    @Autowired
    ProjectService service;
    @Autowired
    RestTemplate restTemplate;
    final String gitMinerUri = "http://localhost:8080/gitminer/v1/projects";

    @GetMapping("/{id}")
    public Project getData(@PathVariable String id, @RequestParam(defaultValue = "5") Integer sinceCommits,
                           @RequestParam(defaultValue = "20") Integer sinceIssues, @RequestParam(defaultValue = "2") Integer maxPages){
        return service.allData(id, sinceCommits, sinceIssues, maxPages);
    }
    @PostMapping("/{id}")
    public Project sendData(@PathVariable String id, @RequestParam(defaultValue = "5") Integer sinceCommits,
                            @RequestParam(defaultValue = "20") Integer sinceIssues, @RequestParam(defaultValue = "2") Integer maxPages){
        Project project= service.allData(id, sinceCommits, sinceIssues, maxPages);
        HttpEntity<Project> request = new HttpEntity<>(project);
       ResponseEntity<Project> response = restTemplate.exchange(gitMinerUri, HttpMethod.POST,request, Project.class);
        return response.getBody();
    }

}

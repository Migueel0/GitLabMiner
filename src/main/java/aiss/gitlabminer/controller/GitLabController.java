package aiss.gitlabminer.controller;
<<<<<<< HEAD

import aiss.gitlabminer.model.Project;
import aiss.gitlabminer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("gitlabminer")
public class GitLabController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ProjectService projectService;
    final String baseUri = "https://gitlab.com/api/v4/";
    final String gitMinerUri = "http://localhost:8080/gitminer";
=======
import aiss.gitlabminer.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import aiss.gitlabminer.service.GitLabService;

@RestController
@RequestMapping("/gitlabminer")
public class GitLabController {

    final String gitMinerUri = "http://localhost:8080/gitminer/v1/projects";
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GitLabService service;
>>>>>>> origin/main

    @GetMapping("/{id}")
    public Project getData(@PathVariable String id, @RequestParam(defaultValue = "5") Integer sinceCommits,
                           @RequestParam(defaultValue = "20") Integer sinceIssues, @RequestParam(defaultValue = "2") Integer maxPages){
<<<<<<< HEAD
        return projectService.allData(id, sinceCommits, sinceIssues, maxPages);
=======
        return service.allData(id, sinceCommits, sinceIssues, maxPages);
>>>>>>> origin/main
    }

    @PostMapping("/{id}")
    public Project sendData(@PathVariable String id, @RequestParam(defaultValue = "5") Integer sinceCommits,
                            @RequestParam(defaultValue = "20") Integer sinceIssues, @RequestParam(defaultValue = "2") Integer maxPages){
<<<<<<< HEAD
        Project newProject = restTemplate.postForObject(gitMinerUri + "/" + id, projectService.allData(id, sinceCommits, sinceIssues, maxPages),Project.class);
        return newProject;
    }


=======

        Project project= service.allData(id, sinceCommits, sinceIssues, maxPages);
        HttpEntity<Project> request = new HttpEntity<>(project);
        ResponseEntity<Project> response = restTemplate.exchange(gitMinerUri, HttpMethod.POST,request, Project.class);
        Project newProject = response.getBody();
        return newProject;
    }
>>>>>>> origin/main
}

package aiss.gitlabminer.controller;

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

    @GetMapping("/{id}")
    public Project getData(@PathVariable String id, @RequestParam(defaultValue = "5") Integer sinceCommits,
                           @RequestParam(defaultValue = "20") Integer sinceIssues, @RequestParam(defaultValue = "2") Integer maxPages){
        return projectService.allData(id, sinceCommits, sinceIssues, maxPages);
    }

    @PostMapping("/{id}")
    public Project sendData(@PathVariable String id, @RequestParam(defaultValue = "5") Integer sinceCommits,
                            @RequestParam(defaultValue = "20") Integer sinceIssues, @RequestParam(defaultValue = "2") Integer maxPages){
        Project newProject = restTemplate.postForObject(gitMinerUri + "/" + id, projectService.allData(id, sinceCommits, sinceIssues, maxPages),Project.class);
        return newProject;
    }


}

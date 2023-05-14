package aiss.gitlabminer.service;

import aiss.gitlabminer.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utils.RESTUtil;

@Service
public class ProjectService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CommitService commitService;
    @Autowired
    IssueService issueService;
    final String baseUri = "https://gitlab.com/api/v4/";

    public Project getProjectById(String id){
        String uri = baseUri + "/projects/" +  id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + RESTUtil.tokenReader("src/test/java/aiss/gitlabminer/token.txt"));
        HttpEntity<Project> request = new HttpEntity<>(null,headers);
        ResponseEntity<Project> response = restTemplate.exchange(uri, HttpMethod.GET,request,Project.class);
        return response.getBody();
    }

    public Project allData(String id, Integer sinceCommits, Integer sinceIssues,Integer maxPages){
        Project data = getProjectById(id);
        data.setCommits(commitService.sinceCommits(id,sinceCommits,maxPages));
        data.setIssues(issueService.sinceIssues(id,sinceIssues,maxPages));
        return data;
    }
}

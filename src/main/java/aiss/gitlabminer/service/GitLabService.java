package aiss.gitlabminer.service;


import aiss.gitlabminer.model.Commit;
import aiss.gitlabminer.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utils.RESTUtil;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class GitLabService {
    @Autowired
    RestTemplate restTemplate;

    final String baseUri = "https://gitlab.com/api/v4/";

    public List<Commit> sinceCommit(String id, Integer days){
        String uri = baseUri + "/projects/" +  id + "/repository/commits";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + RESTUtil.tokenReader("src/test/java/aiss/gitlabminer/token.txt"));
        HttpEntity<Commit[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Commit[]> response =  restTemplate.exchange(uri, HttpMethod.GET, request, Commit[].class);
        List<Commit> commits = Arrays.stream(response.getBody()).toList();

        List<Commit> sinceCommits = commits.stream().filter(x -> RESTUtil
                .StringToLocalDateTime(x.getCommittedDate())
                .isAfter(LocalDateTime.now().minusDays(days))).toList();

        return sinceCommits;

    }

    public List<Issue> sinceIssues(String id, Integer days){
        String uri = baseUri + "/projects/" +  id + "/issues";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + RESTUtil.tokenReader("src/test/java/aiss/gitlabminer/token.txt"));
        HttpEntity<Issue[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Issue[]> response =  restTemplate.exchange(uri, HttpMethod.GET, request, Issue[].class);
        List<Issue> commits = Arrays.stream(response.getBody()).toList();

        List<Issue> sinceIssues = commits.stream().filter(x -> RESTUtil
                .StringToLocalDateTime(x.getUpdatedAt())
                .isAfter(LocalDateTime.now().minusDays(days))).toList();

        return sinceIssues;

    }



}

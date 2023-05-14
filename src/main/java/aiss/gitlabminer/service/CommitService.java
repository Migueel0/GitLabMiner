package aiss.gitlabminer.service;

import aiss.gitlabminer.model.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utils.RESTUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommitService {

    @Autowired
    RestTemplate restTemplate;
    final String baseUri = "https://gitlab.com/api/v4";

    public List<Commit> sinceCommits(String id, Integer days, Integer pages){
        LocalDateTime date = LocalDateTime.now().minusDays(days);
        String uri = baseUri + "/projects/" +  id + "/repository/commits?page=1&per_page=20&since=" + date;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + RESTUtil.tokenReader("src/test/java/aiss/gitlabminer/token.txt"));
        HttpEntity<Commit[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Commit[]> response;
        List<Commit> commits = new ArrayList<>();
        int page = 1;
        while (page <= pages && uri != null){
            response =  restTemplate.exchange(uri,HttpMethod.GET,request,Commit[].class);
            List<Commit> commitPage = Arrays.stream(response.getBody()).toList();
            commits.addAll(commitPage);
            uri =  RESTUtil.getNextPageUrl(response.getHeaders());
            page++;
        }
        return commits;
    }
}

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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommitService {

    @Autowired
    RestTemplate restTemplate;
    final String baseUri = "https://gitlab.com/api/v4/";

    public List<Commit> sinceCommits(String id, Integer days, Integer pages){
        String uri = baseUri + "/projects/" +  id + "/repository/commits";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + RESTUtil.tokenReader("src/test/java/aiss/gitlabminer/token.txt"));
        HttpEntity<Commit[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Commit[]> response =  restTemplate.exchange(uri, HttpMethod.GET, request, Commit[].class);
        //FIRST PAGE
        List<Commit> commits = new ArrayList<>();
        commits.addAll(Arrays.stream(response.getBody()).filter(x -> RESTUtil
                .StringToLocalDateTime(x.getCommittedDate())
                .isAfter(LocalDateTime.now().minusDays(days))).toList());
        int page = 1;
        //ADDING REMAINING PAGES
        while (page <= pages && RESTUtil.getNextPageUrl(response.getHeaders())!= null){
            String url =  RESTUtil.getNextPageUrl(response.getHeaders());
            response =  restTemplate.exchange(url,HttpMethod.GET,request,Commit[].class);
            List<Commit> commitPage = Arrays.stream(response.getBody()).filter(x -> RESTUtil
                    .StringToLocalDateTime(x.getCommittedDate())
                    .isAfter(LocalDateTime.now().minusDays(days))).toList();
            commits.addAll(commitPage);
            page++;
        }
        return commits;
    }
}

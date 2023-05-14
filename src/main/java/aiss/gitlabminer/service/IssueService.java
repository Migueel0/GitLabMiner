package aiss.gitlabminer.service;

import aiss.gitlabminer.model.Issue;
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
public class IssueService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CommentService commentService;
    final String baseUri = "https://gitlab.com/api/v4/";

    public List<Issue> sinceIssues(String id, Integer days, Integer pages){
        String uri = baseUri + "/projects/" +  id + "/issues?id=278964&order_by=created_at&page=1&per_page=20&sort=desc&state=all&with_labels_details=false";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + RESTUtil.tokenReader("src/test/java/aiss/gitlabminer/token.txt"));
        HttpEntity<Issue[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Issue[]> response =  restTemplate.exchange(uri, HttpMethod.GET, request, Issue[].class);
        List<Issue> issues = new ArrayList<>();
        int page = 1;
        while (page <= pages && RESTUtil.getNextPageUrl(response.getHeaders())!= null){
            System.out.println(uri);
            response =  restTemplate.exchange(uri,HttpMethod.GET,request,Issue[].class);
            List<Issue> issuePage = Arrays.stream(response.getBody()).filter(x -> RESTUtil
                    .StringToLocalDateTime(x.getUpdatedAt())
                    .isAfter(LocalDateTime.now().minusDays(days))).toList();
            issues.addAll(issuePage);
            uri =  RESTUtil.getNextPageUrl(response.getHeaders());
            page++;
        }
        issues.forEach(x -> {
            x.setComments(commentService.getNotes(id, x.getIid()));
            String refId = x.getIid();
            x.setRefId(refId);

        } );
        return issues;
    }
}

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
import java.time.LocalDateTime;
import java.util.*;

@Service
public class GitLabService {
    @Autowired
    RestTemplate restTemplate;
    final String baseUri = "https://gitlab.com/api/v4/";

    public List<Commit> sinceCommit(String id, Integer days){
        Integer defaultPages = 2;
        return sinceCommit(id,days,defaultPages);
    }

    public List<Commit> sinceCommit(String id){
        Integer defaultPages = 2;
        Integer defaultDays = 2;
        return sinceCommit(id,defaultDays,defaultPages);
    }

    public List<Commit> sinceCommit(String id, Integer days, Integer pages){
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

    public List<Issue> sinceIssues(String id, Integer days){
        Integer defaultPages = 2;
        return sinceIssues(id,days,defaultPages);
    }

    public List<Issue> sinceIssues(String id){
        Integer defaultPages = 2;
        Integer defaultDays = 20;
        return sinceIssues(id,defaultDays,defaultPages);
    }

    public List<Issue> sinceIssues(String id, Integer days,Integer pages){
        String uri = baseUri + "/projects/" +  id + "/issues";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + RESTUtil.tokenReader("src/test/java/aiss/gitlabminer/token.txt"));
        HttpEntity<Issue[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Issue[]> response =  restTemplate.exchange(uri, HttpMethod.GET, request, Issue[].class);
        List<Issue> issues = new ArrayList<>();

        //FIRST PAGE
        int page = 1;
        issues.addAll(Arrays.stream(response.getBody()).filter(x -> RESTUtil
                .StringToLocalDateTime(x.getUpdatedAt())
                .isAfter(LocalDateTime.now().minusDays(days))).toList());


        //ADDING REMAINING PAGES
        while (page <= pages && RESTUtil.getNextPageUrl(response.getHeaders())!= null){
            String url =  RESTUtil.getNextPageUrl(response.getHeaders());
            response =  restTemplate.exchange(url,HttpMethod.GET,request,Issue[].class);
            List<Issue> issuePage = Arrays.stream(response.getBody()).filter(x -> RESTUtil
                    .StringToLocalDateTime(x.getUpdatedAt())
                    .isAfter(LocalDateTime.now().minusDays(days))).toList();
            issues.addAll(issuePage);
            page++;
        }

        return issues;

    }



}

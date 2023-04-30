package aiss.gitlabminer.service;
import aiss.gitlabminer.model.Comment;
import aiss.gitlabminer.model.Commit;
import aiss.gitlabminer.model.Issue;
import aiss.gitlabminer.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.ProjectingArgumentResolverRegistrar;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import utils.RESTUtil;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RestController
@RequestMapping("gitlabminer")
public class GitLabService {
    @Autowired
    RestTemplate restTemplate;
    final String baseUri = "https://gitlab.com/api/v4/";
    final String gitMinerUri = "http://localhost:8080/gitminer";

    public Project getProjectById(String id){
        String uri = baseUri + "/projects/" +  id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + RESTUtil.tokenReader("src/test/java/aiss/gitlabminer/token.txt"));
        HttpEntity<Project> request = new HttpEntity<>(null,headers);
        ResponseEntity<Project> response = restTemplate.exchange(uri,HttpMethod.GET,request,Project.class);
        return response.getBody();
    }

    public Project allData(String id, Integer sinceCommits, Integer sinceIssues,Integer maxPages){
        Project data = getProjectById(id);
        data.setCommits(sinceCommits(id,sinceCommits,maxPages));
        data.setIssues(sinceIssues(id,sinceIssues,maxPages));
        return data;
    }

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

    public List<Comment> getNotes(String id, String iid){
        String uri = baseUri + "/projects/" +  id + "/issues/" + iid + "/notes";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + RESTUtil.tokenReader("src/test/java/aiss/gitlabminer/token.txt"));
        HttpEntity<String[]> request = new HttpEntity<>(null,headers);
        ResponseEntity<Comment[]> response = restTemplate.exchange(uri,HttpMethod.GET,request,Comment[].class);
        return Arrays.stream(response.getBody()).toList();
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
        issues.forEach(x -> x.setComments(getNotes(id, x.getRefId())));
        return issues;
    }

    @GetMapping("/{id}")
    public Project getData(@PathVariable String id, @RequestParam(defaultValue = "5") Integer sinceCommits,
                           @RequestParam(defaultValue = "20") Integer sinceIssues, @RequestParam(defaultValue = "2") Integer maxPages){
        return allData(id, sinceCommits, sinceIssues, maxPages);
    }

    @PostMapping("/{id}")
    public Project sendData(@PathVariable String id, @RequestParam(defaultValue = "5") Integer sinceCommits,
                            @RequestParam(defaultValue = "20") Integer sinceIssues, @RequestParam(defaultValue = "2") Integer maxPages){
        Project newProject = restTemplate.postForObject(gitMinerUri + "/" + id,allData(id, sinceCommits, sinceIssues, maxPages),Project.class);
        return newProject;
    }


}

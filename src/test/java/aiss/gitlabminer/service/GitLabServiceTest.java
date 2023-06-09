package aiss.gitlabminer.service;

import aiss.gitlabminer.model.Comment;
import aiss.gitlabminer.model.Commit;
import aiss.gitlabminer.model.Issue;
import aiss.gitlabminer.model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import utils.RESTUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GitLabServiceTest {

    @Autowired
    CommentService commentService;
    @Autowired
    CommitService commitService;
    @Autowired
    IssueService issueService;
    @Autowired
    ProjectService projectService;

    @Autowired
    RestTemplate restTemplate;
    final String baseUri = "https://gitlab.com/api/v4/";

    @Test
    @DisplayName("Testing get project by id")
    void getProjectByIdTest(){

        //Define some parameters
        String id = "278964";
        String name = "GitLab";
        String uri = baseUri + "/projects/" +  id;

        //Consuming API in order to get the status code
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + RESTUtil.tokenReader("src/test/java/aiss/gitlabminer/token.txt"));
        HttpEntity<Project> request = new HttpEntity<>(null,headers);
        ResponseEntity<Project> response = restTemplate.exchange(uri, HttpMethod.GET,request,Project.class);
        HttpStatus status = response.getStatusCode();

        //Checking the status code
        assertEquals(HttpStatus.OK, status,"Status code must be OK");

        //Checking response fields
        assertTrue(response.hasBody());
        Project project = projectService.getProjectById(id);
        assertNotNull(project.getId(), "Id cannot be null");
        assertNotNull(project.getName(), "Name cannot be null");
        assertEquals(id,project.getId(),"Provided id must be equal the project Id");
        assertEquals(name,project.getName(),"Provided name must be equal the project name");

        //Issues and commits must be empty because this method doesn't set both arrays
        assertTrue(project.getCommits().isEmpty());
        assertTrue(project.getIssues().isEmpty());

        System.out.println("Test passed");

    }

    @Test
    @DisplayName("Testing commits")
    void sinceCommitsTest(){
        String id = "278964";
        Integer days = 1;
        Integer pages = 30;
        List<Commit> commits = commitService.sinceCommits(id,days,pages);
        LocalDate date = LocalDate.now().minusDays(days);
        String uri = baseUri + "/projects/" +  id + "/repository/commits?page=1&per_page=20&since=" + date;

        //Consuming API in order to get the status code
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + RESTUtil.tokenReader("src/test/java/aiss/gitlabminer/token.txt"));
        HttpEntity<Project> request = new HttpEntity<>(null,headers);
        ResponseEntity<Commit[]> response = restTemplate.exchange(uri, HttpMethod.GET,request, Commit[].class);
        HttpStatus status = response.getStatusCode();

        //Checking the status code
        assertEquals(HttpStatus.OK, status,"Status code must be OK");


        //Checking response fields
        for(Commit commit: commits){

            assertNotNull(commit.getId(),"Id cannot be null");

            assertEquals(40,commit.getId().length(),"Id length must be 40");

            assertTrue(commit.getWebUrl().contains(commit.getId()),"Web Url must contain commit id");

            assertTrue(RESTUtil.StringToLocalDateTime(commit.getCommittedDate()).isAfter(LocalDateTime.now().minusDays(days)),
                    "The committed date cannot be before the days specified in the parameters");

        }

        System.out.println("Test passed");

    }

    @Test
    @DisplayName("Testing issues")
    void sinceIssuesTest(){
        String id = "278964";
        Integer days = 20;
        Integer pages = 1;
        List<Issue> issues = issueService.sinceIssues(id,days,pages);

        String uri = baseUri + "/projects/" +  id + "/issues";

        //Consuming API in order to get the status code
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + RESTUtil.tokenReader("src/test/java/aiss/gitlabminer/token.txt"));
        HttpEntity<Project> request = new HttpEntity<>(null,headers);
        ResponseEntity<Commit[]> response = restTemplate.exchange(uri, HttpMethod.GET,request, Commit[].class);
        HttpStatus status = response.getStatusCode();

        //Checking the status code
        assertEquals(HttpStatus.OK, status,"Status code must be OK");

        for(Issue issue: issues){

            assertNotNull(issue.getId(),"Id cannot be null");

            assertNotNull(issue.getRefId(),"Iid cannot be null");

            assertTrue(issue.getWebUrl().contains(issue.getRefId()),"Web Url must contain commit iid");

            assertTrue(RESTUtil.StringToLocalDateTime(issue.getUpdatedAt()).isAfter(LocalDateTime.now().minusDays(days)),
                    "The issue update date cannot be before the days specified in the parameters");

            //An opened issue cannot have close date
            assertTrue(issue.getState() == "opened"?issue.getClosedAt() == null:true);

            //A closed issue must have close date
            assertTrue(issue.getState() == "closed"?issue.getClosedAt() != null:true);

        }

        System.out.println("Test passed");
    }

    @Test
    @DisplayName("Testing all data")
    void allDataTest(){

        String id = "278964";
        String name = "GitLab";

        Project data = projectService.allData("278964",5,20,2);

        assertNotNull(data.getId(), "Id cannot be null");
        assertNotNull(data.getName(), "Name cannot be null");
        assertEquals(id,data.getId(),"Provided id must be equal the project Id");
        assertEquals(name,data.getName(),"Provided name must be equal the project name");


        System.out.println("Test passed");
    }

    @Test
    @DisplayName("Testing authorization")

    void authorizationTest(){

        String id = "278964";
        String iid= "409313";

        //Checking response with correct token authorization
        String uri = baseUri + "/projects/" +  id + "/issues/" + iid + "/notes";
        HttpHeaders headersCorrectToken  = new HttpHeaders();
        headersCorrectToken.set("Authorization", "Bearer " + RESTUtil.tokenReader("src/test/java/aiss/gitlabminer/token.txt"));
        HttpEntity<String[]> requestValidToken = new HttpEntity<>(null,headersCorrectToken);
        ResponseEntity<Comment[]> responseValidToken = restTemplate.exchange(uri,HttpMethod.GET,requestValidToken,Comment[].class);
        HttpStatus okStatus = responseValidToken.getStatusCode();

        //Checking the status code
        assertEquals(HttpStatus.OK, okStatus,"Status code must be OK");


        ///Checking response with no token authorization
        HttpHeaders headersNoToken  = new HttpHeaders();
        HttpEntity<String[]> requestNoToken = new HttpEntity<>(null,headersNoToken);

        try {
            restTemplate.exchange(uri, HttpMethod.GET, requestNoToken, Comment[].class);
        }catch (HttpClientErrorException e) {
            //Checking the status code
            HttpStatus errorStatus = e.getStatusCode();
            assertEquals(HttpStatus.UNAUTHORIZED, errorStatus, "Status code must be unauthorized");
        }


        //Checking response with not valid token
        HttpHeaders headersNotValidToken  = new HttpHeaders();
        headersNotValidToken.set("Authorization", "This is an invalid token");
        HttpEntity<String[]> requestNotValidToken = new HttpEntity<>(null,headersNotValidToken);
        try {
            restTemplate.exchange(uri, HttpMethod.GET, requestNotValidToken, Comment[].class);
        }catch (HttpClientErrorException e) {
            //Checking the status code
            HttpStatus errorStatus = e.getStatusCode();
            assertEquals(HttpStatus.UNAUTHORIZED, errorStatus, "Status code must be unauthorized");
        }

        System.out.println("Test passed");

    }

}
package aiss.gitlabminer.service;

import aiss.gitlabminer.model.Commit;
import aiss.gitlabminer.model.Issue;
import aiss.gitlabminer.model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import utils.RESTUtil;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GitLabServiceTest {
    @Autowired
    GitLabService gitLabService;
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
        Project project = gitLabService.getProjectById(id);
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
        List<Commit> commits =  gitLabService.sinceCommits(id,days,pages);

        String uri = baseUri + "/projects/" +  id + "/repository/commits";

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
        List<Issue> issues =  gitLabService.sinceIssues(id,days,pages);

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
    @DisplayName("Display all data")
    void allDataTest(){
        Project data =  gitLabService.allData("4207231",5,20,1);
        System.out.println(data);
    }

}
package aiss.gitlabminer.service;

import aiss.gitlabminer.model.Commit;
import aiss.gitlabminer.model.Issue;
import aiss.gitlabminer.model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GitLabServiceTest {
    @Autowired
    GitLabService gitLabService;

    @Test
    @DisplayName("Display all commits")
    void sinceCommitsTest(){
        List<Commit> commits =  gitLabService.sinceCommits("278964",1,30);
        assertTrue(!commits.isEmpty());
        System.out.println(commits);
    }

    @Test
    @DisplayName("Display all issues")
    void sinceIssuesTest(){
        List<Issue> issues =  gitLabService.sinceIssues("278964",20,1);
        assertTrue(!issues.isEmpty());
        System.out.println(issues);
    }

    @Test
    @DisplayName("Display all data")
    void allDataTest(){
        Project data =  gitLabService.allData("4207231",5,20,1);
        System.out.println(data);
    }

}
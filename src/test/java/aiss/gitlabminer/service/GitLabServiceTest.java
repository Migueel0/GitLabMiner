package aiss.gitlabminer.service;

import aiss.gitlabminer.model.Commit;
import aiss.gitlabminer.model.Issue;
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
        List<Commit> commits =  gitLabService.sinceCommit("278964",10);
        assertTrue(!commits.isEmpty());
        System.out.println(commits);
    }

    @Test
    @DisplayName("Display all issues")
    void sinceIssuesTest(){
        List<Issue> issues =  gitLabService.sinceIssues("278964",20);
        assertTrue(!issues.isEmpty());
        System.out.println(issues);
    }

}
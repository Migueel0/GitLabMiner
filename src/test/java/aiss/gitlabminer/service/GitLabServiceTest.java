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
        List<Issue> issues =  gitLabService.sinceIssues("278964",20,30);
        assertTrue(!issues.isEmpty());
        System.out.println(issues);
    }
    @Test
    @DisplayName("Display all data with all parameters as default value")
    void allDataVoidTest(){
        Project data =  gitLabService.allData("4207231");
        System.out.println(data);
    }
    @Test
    @DisplayName("Display all data with sinceCommits and maxPages as default value")
    void allDataVoidCommitsPagesTest(){
        Project data =  gitLabService.allDataIssues("4207231",30);
        System.out.println(data);
    }
    @Test
    @DisplayName("Display all data with sinceIssues and maxPages as default value")
    void allDataVoidIssuesPagesTest(){
        Project data =  gitLabService.allDataCommits("4207231",5);
        System.out.println(data);
    }

    @Test
    @DisplayName("Display all data with sinceCommits and sinceIssues as default value")
    void allDataVoidIssuesCommitsTest(){
        Project data =  gitLabService.allDataPages("4207231",3);
        System.out.println(data);
    }

    @Test
    @DisplayName("Display all data with sinceIssues default value")
    void allDataVoidIssuesTest(){
        Project data =  gitLabService.allDataPagesCommits("4207231",5,3);
        System.out.println(data);
    }

    @Test
    @DisplayName("Display all data with sinceCommits as default value")
    void allDataVoidCommitsTest(){
        Project data =  gitLabService.allDataPagesIssues("4207231",30,3);
        System.out.println(data);
    }

    @Test
    @DisplayName("Display all data with max pages as default value")
    void allDataVoidPagesTest(){
        Project data =  gitLabService.allData("4207231",5,30);
        System.out.println(data);
    }

    @Test
    @DisplayName("Display all data")
    void allDataTest(){
        Project data =  gitLabService.allData("4207231",5,30,4);
        System.out.println(data);
    }

}
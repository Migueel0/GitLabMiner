package aiss.gitlabminer.service;

import aiss.gitlabminer.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import utils.RESTUtil;

import java.util.Arrays;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    RestTemplate restTemplate;
    final String baseUri = "https://gitlab.com/api/v4/";
    public List<Comment> getNotes(String id, String iid){
        String uri = baseUri + "/projects/" +  id + "/issues/" + iid + "/notes";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + RESTUtil.tokenReader("src/test/java/aiss/gitlabminer/token.txt"));
        HttpEntity<String[]> request = new HttpEntity<>(null,headers);
        ResponseEntity<Comment[]> response = restTemplate.exchange(uri, HttpMethod.GET,request,Comment[].class);
        return Arrays.stream(response.getBody()).toList();
    }

}
